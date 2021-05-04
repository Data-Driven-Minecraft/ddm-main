package com.github.mcc.ddm.block

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material

class BlockDataEntry {
    val settings: FabricBlockSettings = FabricBlockSettings.of(Material.AIR)
    val states = mutableMapOf<String, DefaultedProperty<*, *>>()
}