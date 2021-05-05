package com.github.mcc.ddm.block

import com.github.mcc.ddm.BaseDataLoader
import com.github.mcc.ddm.duck.AbstractBlockSettingsDuck
import com.github.mcc.ddm.extension.materialFromString
import com.google.gson.JsonObject
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * The data loader for blocks, which go in the pack's `blocks` folder.
 */
class BlockDataLoader : BaseDataLoader<BlockDataEntry, CustomBlock>("blocks") {
    override fun read(json: JsonObject): BlockDataEntry {
        val data = BlockDataEntry()
        json.getAsJsonObject("properties")?.let {
            it.get("hardness")?.let {data.settings.hardness(it.asFloat)}
            it.get("resistance")?.let {data.settings.resistance(it.asFloat)}
            it.get("material")?.let {
                (data.settings as AbstractBlockSettingsDuck).material = materialFromString(it.asString)
            }
        }
        json.getAsJsonObject("blockstates")?.let {
            for ((state, values) in it.entrySet()) {
                val values = values.asJsonObject
                val default = values.get("default")
                when (val ty = values.get("type").asString) {
                    "list" -> {
                        data.states[state] = DefaultedProperty(SetProperty(
                            state,
                            values.getAsJsonArray("values").map { it.asString }.toList()
                        ), default.asString)
                    }
                    else -> throw Exception("Unknown type $ty")
                }
            }
        }
        return data
    }

    override fun register(server: MinecraftServer, ident: Identifier, data: BlockDataEntry): CustomBlock {
        val block = CustomBlock(data)
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
        return block
    }

    override fun unregister(server: MinecraftServer, ident: Identifier, entry: CustomBlock) {
        Registry.BLOCK.entries.removeAll {
            it.key.value == ident
        }
        entry.item?.let { _ ->
            Registry.ITEM.entries.removeAll {
                it.key.value == ident
            }
        }
    }
}