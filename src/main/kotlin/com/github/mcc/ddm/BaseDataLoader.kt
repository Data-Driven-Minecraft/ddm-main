package com.github.mcc.ddm

import com.google.gson.Gson
import com.google.gson.JsonElement
import net.minecraft.loot.LootGsons
import net.minecraft.resource.JsonDataLoader
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import net.minecraft.util.profiler.Profiler

/**
 * A base class for data loaders to extend. Handles loading of the data.
 * @constructor Initialises the loader with a gson parser and folder.
 * @param <T> The data type used, e.g [net.minecraft.block.Block].
 * @param folder The folder name to load from (e.g `blocks`)
 */
abstract class BaseDataLoader<T>(folder: String): JsonDataLoader(GSON, folder) {
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
    protected val entries = mutableMapOf<Identifier, DataEntry>()

    /**
     * Load parsed files from a datapack into [entries].
     * @param loader A map of identifier (file path) to parsed JSON.
     * @param manager A resource manager for querying information about resources.
     * @param profiler A profiler to log to.
     */
    override fun apply(loader: MutableMap<Identifier, JsonElement>, manager: ResourceManager, profiler: Profiler) {
        TODO("Not yet implemented")
    }
}