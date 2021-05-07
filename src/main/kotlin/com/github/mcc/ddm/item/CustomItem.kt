package com.github.mcc.ddm.item

import com.github.mcc.ddm.event.Event
import com.github.mcc.ddm.extension.append
import com.github.mcc.ddm.extension.eventManager
import com.github.mcc.ddm.extension.prepend
import com.github.mcc.ddm.extension.resourceManager
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

/**
 * A custom item class used to create registered items.
 * @constructor Takes in an [ItemDataEntry] to produce the custom item.
 * @param server The server to register events on
 * @property data The data to use for this item.
 * @property id The identifier this item will be registered at (for event registration).
 */
class CustomItem(server: MinecraftServer, private val id: Identifier, private val data: ItemDataEntry) : Item(data.settings) {
    /**
     * Register events for the item (only call in constructor)
     */
    private fun addEvent(server: MinecraftServer, id: Identifier, vararg events: String) {
        val eventManager = server.resourceManager.eventManager
        for (event in events) {
            eventManager.register(server, id.prepend("items").append(event), Event.empty())
        }
    }

    init {
        addEvent(
            server, id,
            "used", "attack", "drop"
        )
    }

    private fun emitEvent(event: String, world: World, target: Entity, pos: Vec3d) {
        if (!world.isClient) {
            assert(world.server != null)
            val source = world.server!!.commandSource
                .withEntity(target)
                .withPosition(pos)
                .withSilent()
            world.server!!.resourceManager.eventManager.emit(id.prepend("items").append(event), source)
        }
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        emitEvent("used", world, user, user.pos)
        return super.use(world, user, hand)
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        emitEvent("attack", attacker.world, target, target.pos)
        return super.postHit(stack, target, attacker)
    }

    fun drop(world: World, entity: ItemEntity) {
        emitEvent("drop", world, entity, entity.pos)
    }
}