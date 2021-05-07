package com.github.mcc.ddm

import com.github.mcc.ddm.extension.blockManager
import com.github.mcc.ddm.extension.eventManager
import com.github.mcc.ddm.extension.itemManager
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents

class DataDriven: ModInitializer {
    override fun onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register {
            it.reloadResources(it.dataPackManager.enabledNames)
        }
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register { server, resourceManager, _ ->
            try {
                resourceManager.eventManager.unregisterAll(server)
                resourceManager.eventManager.registerAll(server)

                resourceManager.itemManager.unregisterAll(server)
                resourceManager.itemManager.registerAll(server)

                resourceManager.blockManager.unregisterAll(server)
                resourceManager.blockManager.registerAll(server)
            } catch (e: Exception) {
                println("Failed to reload data driven parts:\n$e")
            }
        }
    }
}