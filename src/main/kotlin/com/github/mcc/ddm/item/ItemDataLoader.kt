package com.github.mcc.ddm.item

import com.github.mcc.ddm.BaseDataLoader
import com.google.gson.JsonObject
import net.minecraft.server.MinecraftServer
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * A [data loader][BaseDataLoader] for [Item][net.minecraft.item.Item]s, which go in the "items" folder.
 * @see CustomItem
 * @see ItemDataEntry
 */
class ItemDataLoader: BaseDataLoader<ItemDataEntry, CustomItem>("items") {
    override fun read(json: JsonObject): ItemDataEntry {
        val data = ItemDataEntry()
        json.get("stack_size")?.let { data.settings.maxCount(it.asInt) }
        return data
    }

    override fun register(server: MinecraftServer, ident: Identifier, data: ItemDataEntry): CustomItem {
        val item = CustomItem(server, ident, data)
        Registry.register(Registry.ITEM, ident, item)
        return item
    }

    override fun unregister(server: MinecraftServer, ident: Identifier, entry: CustomItem) {

    }
}