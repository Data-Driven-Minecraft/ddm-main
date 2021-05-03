package com.github.mcc.ddm.duck

import com.github.mcc.ddm.block.BlockDataLoader

interface ServerResourceManagerDuck {
    val blockManager: BlockDataLoader
}