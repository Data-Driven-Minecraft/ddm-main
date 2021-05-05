package com.github.mcc.ddm.item

import com.github.mcc.ddm.BaseDataLoader
import com.google.gson.JsonObject
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * A [data loader][BaseDataLoader] for [Item][net.minecraft.item.Item]s, which go in the "items" folder.
 * @see CustomItem
 * @see ItemDataEntry
 */
class ItemDataLoader: BaseDataLoader<ItemDataEntry, CustomItem>("items") {

    class IdentifierParseException(private val ident: String): Exception() {
        override val message: String
            get() = "Could not parse ident $ident"
    }

    private fun JsonObject.event(name: String): Identifier? {
        return get(name)?.let {Identifier.tryParse(it.asString) ?: throw IdentifierParseException(it.asString)}
    }

    override fun read(json: JsonObject): ItemDataEntry {
        val data = ItemDataEntry()
        json.getAsJsonObject("events")?.let { events ->
            data.events.used = events.event("used")
            data.events.attack = events.event("attack")
            data.events.drop = events.event("drop")
        }
        json.get("stack_size")?.let { data.settings.maxCount(it.asInt) }
        return data
    }

    override fun register(server: MinecraftServer, ident: Identifier, data: ItemDataEntry): CustomItem {
        val item = CustomItem(data)
        Registry.register(Registry.ITEM, ident, item)
        return item
    }

    override fun unregister(server: MinecraftServer, ident: Identifier, entry: CustomItem) {

    }
}