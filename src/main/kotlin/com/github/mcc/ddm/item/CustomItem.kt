package com.github.mcc.ddm.item

import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

/**
 * A custom item class used to create registered items.
 * @constructor Takes in an [ItemDataEntry] to produce the custom item.
 * @param data The data to use for this item.
 */
class CustomItem(private val data: ItemDataEntry): Item(data.settings) {
    private fun event(world: World, id: Identifier?, target: Entity, position: Vec3d) {
        id?.let {
            if (!world.isClient) {
                val manager = world.server!!.commandFunctionManager
                val function = manager.getFunction(id)
                function.ifPresent {
                    manager.execute(
                        it,
                        world.server!!.commandSource
                            .withEntity(target)
                            .withPosition(position)
                            .withSilent()
                    )
                }
            }
        }
    }

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        event(world, data.events.used, user, user.pos)
        return super.use(world, user, hand)
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        event(attacker.world, data.events.attack, target, target.pos)
        return super.postHit(stack, target, attacker)
    }

    fun drop(world: World, entity: ItemEntity) {
        event(world, data.events.drop, entity, entity.pos);
    }
}