package com.github.mcc.ddm.block

import com.github.mcc.ddm.BaseDataLoader
import com.google.gson.JsonElement
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.resource.ResourceManager
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.util.profiler.Profiler
import net.minecraft.util.registry.Registry

/**
 * The data loader for blocks, which go in the pack's `blocks` folder.
 */
class BlockDataLoader: BaseDataLoader<BlockDataEntry, Block>("blocks") {
    override fun read(json: JsonElement): BlockDataEntry {

        return BlockDataEntry()
    }

    override fun register(server: MinecraftServer, ident: Identifier, data: BlockDataEntry) {
        Registry.register(
            Registry.BLOCK,
            ident,
            Block(FabricBlockSettings.of(Material.METAL))
        )
//        server.registryManager[Registry.BLOCK.key]
    }
}