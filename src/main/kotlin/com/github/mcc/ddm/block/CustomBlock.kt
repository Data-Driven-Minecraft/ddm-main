package com.github.mcc.ddm.block

import net.minecraft.block.Block
import net.minecraft.item.BlockItem

class CustomBlock(settings: Settings): Block(settings) {
    var item: BlockItem? = null
}