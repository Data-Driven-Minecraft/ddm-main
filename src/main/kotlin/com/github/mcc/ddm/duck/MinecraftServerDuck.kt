package com.github.mcc.ddm.duck

import net.minecraft.resource.ServerResourceManager

interface MinecraftServerDuck {
    val serverResourceManager: ServerResourceManager
}