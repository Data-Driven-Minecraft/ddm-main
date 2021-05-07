package com.github.mcc.ddm.event

import com.github.mcc.ddm.BaseDataLoader
import com.github.mcc.ddm.extension.idents
import com.github.mcc.ddm.extension.list
import com.github.mcc.ddm.extension.toIdentifier
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.loot.condition.LootCondition
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextParameters
import net.minecraft.loot.context.LootContextTypes.COMMAND
import net.minecraft.server.MinecraftServer
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.util.Identifier

/**
 * A [data loader][BaseDataLoader] for [Event]s, which go in the "items" folder.
 */
class EventDataLoader : BaseDataLoader<Event, Event>("events") {
    /**
     * A map of triggers to events that rely on them.
     */
    private val triggerMap = mutableMapOf<Identifier, MutableSet<Identifier>>()

    /**
     * Read to create the data type from a [JsonElement]
     * @param json The parsed json
     * @return The constructed [Event]
     * @throws com.google.gson.JsonSyntaxException if a predicate fails to parse
     * @throws com.github.mcc.ddm.extension.IdentifierParseException if an identifier fails to parse
     * @throws UnsupportedOperationException if something is the wrong data type
     */
    override fun read(json: JsonObject): Event {
        val listeners = json.getAsJsonArray("listeners").idents()
        val triggers = json.getAsJsonArray("triggers").list {
            val source = it.get("source").asString.toIdentifier()
            val conditions = it.getAsJsonArray("conditions").list { cond ->
                GSON.fromJson(cond, LootCondition::class.java)
            }
            EventTrigger(source, conditions)
        }
        return Event(triggers, listeners)
    }

    /**
     * Emit an event, recursively calling all events that listen to it.
     * @param event The [Identifier] of the event to call
     * @param source The [ServerCommandSource] to execute functions and predicates with
     * @throws NoSuchElementException if a listener doesn't exist
     * @throws NullPointerException if an invalid event id is referenced
     */
    fun emit(event: Identifier, source: ServerCommandSource) {
        for (listener in entries[event]!!.listeners) {
            source.minecraftServer.commandFunctionManager.execute(
                source.minecraftServer.commandFunctionManager.getFunction(listener).get(),
                source
            )
        }
        triggerMap[event]?.let {
            for (triggered_id in it) {
                val triggered = entries[triggered_id]!!
                var passed = true
                for (condition in triggered.triggers.first { it.source == event }.conditions) {
                    val context = LootContext.Builder(source.world)
                        .parameter(LootContextParameters.ORIGIN, source.position)
                        .optionalParameter(LootContextParameters.THIS_ENTITY, source.entity)
                        .build(COMMAND)
                    if (!condition.test(context)) {
                        passed = false
                    }
                }
                if (passed) emit(triggered_id, source)
            }
        }
    }


    override fun register(server: MinecraftServer, ident: Identifier, data: Event): Event {
        entries[ident] = data
        for (trigger in data.triggers) {
            if (trigger.source !in triggerMap) triggerMap[trigger.source] = mutableSetOf()
            triggerMap[trigger.source]!!.add(ident)
        }
        return data
    }

    override fun unregister(server: MinecraftServer, ident: Identifier, entry: Event) {
        for ((_, remove) in triggerMap.filter { it.value.contains(entry) }) {
            remove.remove(entry)
        }
    }
}