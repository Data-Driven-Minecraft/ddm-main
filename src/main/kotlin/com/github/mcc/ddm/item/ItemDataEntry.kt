package com.github.mcc.ddm.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings

/**
 * Holds data for custom items.
 * @see ItemDataLoader
 */
class ItemDataEntry {
    /**
     * The [FabricItemSettings] to use on the outputted [CustomItem]
     */
    val settings: FabricItemSettings = FabricItemSettings()
}