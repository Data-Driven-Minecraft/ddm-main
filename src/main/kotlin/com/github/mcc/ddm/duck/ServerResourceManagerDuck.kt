package com.github.mcc.ddm.duck

import com.github.mcc.ddm.block.BlockDataLoader
import com.github.mcc.ddm.item.ItemDataLoader

interface ServerResourceManagerDuck {
    val blockManager: BlockDataLoader
    val itemManager: ItemDataLoader
}