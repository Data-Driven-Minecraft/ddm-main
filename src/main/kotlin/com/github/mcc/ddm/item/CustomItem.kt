package com.github.mcc.ddm.item

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

/**
 * A custom item class used to create registered items.
 * @constructor Takes in an [ItemDataEntry] to produce the custom item.
 * @param data The data to use for this item.
 */
class CustomItem(private val data: ItemDataEntry): Item(data.settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        data.events.used?.let { id ->
            if (!world.isClient) {
                val manager = world.server!!.commandFunctionManager
                val function = manager.getFunction(id)
                function.ifPresent {
                    manager.execute(
                        it,
                        world.server!!.commandSource
                            .withEntity(user)
                            .withPosition(user.pos)
                            .withSilent()
                    )
                }
            }
        }
        return super.use(world, user, hand)
    }
}