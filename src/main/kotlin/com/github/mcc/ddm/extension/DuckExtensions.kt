package com.github.mcc.ddm.extension

import com.github.mcc.ddm.block.BlockDataLoader
import com.github.mcc.ddm.duck.MinecraftServerDuck
import com.github.mcc.ddm.duck.ServerResourceManagerDuck
import com.github.mcc.ddm.event.EventDataLoader
import com.github.mcc.ddm.item.ItemDataLoader
import net.minecraft.resource.ServerResourceManager
import net.minecraft.server.MinecraftServer

val MinecraftServer.resourceManager: ServerResourceManager get() = (this as MinecraftServerDuck).serverResourceManager
val ServerResourceManager.blockManager: BlockDataLoader get() = (this as ServerResourceManagerDuck).blockManager
val ServerResourceManager.itemManager: ItemDataLoader get() = (this as ServerResourceManagerDuck).itemManager
val ServerResourceManager.eventManager: EventDataLoader get() = (this as ServerResourceManagerDuck).eventManager