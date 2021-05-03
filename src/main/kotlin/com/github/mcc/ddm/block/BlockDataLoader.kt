package com.github.mcc.ddm.block

import com.github.mcc.ddm.BaseDataLoader
import com.google.gson.JsonElement
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.resource.ResourceManager
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.util.profiler.Profiler
import net.minecraft.util.registry.Registry

/**
 * The data loader for blocks, which go in the pack's `blocks` folder.
 */
class BlockDataLoader: BaseDataLoader<BlockDataEntry, CustomBlock>("blocks") {
    override fun read(json: JsonElement): BlockDataEntry {

        return BlockDataEntry()
    }

    override fun register(server: MinecraftServer, ident: Identifier, data: BlockDataEntry) {
        val block = CustomBlock(FabricBlockSettings.of(Material.METAL))
        val item = BlockItem(block, FabricItemSettings())
        block.item = item
        Registry.register(
            Registry.BLOCK,
            ident,
            block
        )
        Registry.register(
            Registry.ITEM,
            ident,
            item
        )
    }

    override fun unregister(server: MinecraftServer, ident: Identifier, entry: CustomBlock) {
        Registry.BLOCK.entries.remove(entry) // why yellow
        entry.item?.let {
            Registry.ITEM.entries.remove(it)
        }
    }
}