package com.github.mcc.ddm

import com.google.gson.Gson
import com.google.gson.JsonElement
import net.minecraft.loot.LootGsons
import net.minecraft.resource.JsonDataLoader
import net.minecraft.resource.ResourceManager
import net.minecraft.server.MinecraftServer
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.profiler.Profiler

/**
 * A base class for data loaders to extend. Handles loading of the data.
 * @constructor Initialises the loader with a gson parser and folder.
 * @param <E> The data entry type, storing property information.
 * @param <T> The data type used, e.g [net.minecraft.block.Block].
 * @param folder The folder name to load from (e.g `blocks`)
 */
abstract class BaseDataLoader<E, T>(folder: String): JsonDataLoader(GSON, folder) {
    companion object {
        // Probably want to change this, it's for loot tables
        // I have no idea what to though, but seems to work for game events
        /**
         * The GSON parser.
         */
        @JvmStatic
        val GSON: Gson = LootGsons.getTableGsonBuilder().create()
    }

    /**
     * The loaded custom entries
     */
    private val entries = mutableMapOf<Identifier, E>()

    /**
     * Load parsed files from a datapack into [entries].
     * @param loader A map of identifier (file path) to parsed JSON.
     * @param manager A resource manager for querying information about resources.
     * @param profiler A profiler to log to.
     */
    final override fun apply(loader: MutableMap<Identifier, JsonElement>, manager: ResourceManager, profiler: Profiler) {
        for ((ident, json) in loader) {
            entries[ident] = read(json)
        }
    }

    /**
     * Register all of this loader's entries.
     * @param server The server to register on.
     */
    fun registerAll(server: MinecraftServer) {
        for ((ident, data) in entries) register(server, ident, data)
    }

    /**
     * Read to create the data type from a [JsonElement]
     * @param json The parsed json
     */
    abstract fun read(json: JsonElement): E

    /**
     * Register this type's [T] from its identifier and data (at the end of reloading).
     * @param server The server to register on.
     * @param ident The identifier of the item.
     * @param data The data class representing its parsed properties.
     */
    abstract fun register(server: MinecraftServer, ident: Identifier, data: E)
}