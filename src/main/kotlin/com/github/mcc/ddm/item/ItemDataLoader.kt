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

    class IdentifierParseException(private val ident: String): Exception() {
        override val message: String
            get() = "Could not parse ident $ident"
    }

    override fun read(json: JsonObject): ItemDataEntry {
        val data = ItemDataEntry()
        json.getAsJsonObject("events")?.let { events ->
            events.get("used")?.let {data.events.used = Identifier.tryParse(it.asString) ?: throw IdentifierParseException(it.asString)}
        }
        return data
    }

    override fun register(server: MinecraftServer, ident: Identifier, data: ItemDataEntry): CustomItem {
        val item = CustomItem(data.settings, data.events)
        Registry.register(Registry.ITEM, ident, item)
        return item
    }

    override fun unregister(server: MinecraftServer, ident: Identifier, entry: CustomItem) {

    }
}