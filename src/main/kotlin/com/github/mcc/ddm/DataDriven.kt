package com.github.mcc.ddm

import com.github.mcc.ddm.duck.MinecraftServerDuck
import com.github.mcc.ddm.duck.ServerResourceManagerDuck
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents

class DataDriven: ModInitializer {
    override fun onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register {
            val srm = (it as MinecraftServerDuck).serverResourceManager as ServerResourceManagerDuck
            it.reloadResources(it.dataPackManager.enabledNames)
        }
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register { server, resourceManager, _ ->
            (resourceManager as ServerResourceManagerDuck).itemManager.unregisterAll(server)
            (resourceManager as ServerResourceManagerDuck).blockManager.unregisterAll(server)
            (resourceManager as ServerResourceManagerDuck).itemManager.registerAll(server)
            (resourceManager as ServerResourceManagerDuck).blockManager.registerAll(server)
        }
    }
}