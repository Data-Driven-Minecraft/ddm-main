package com.github.mcc.ddm.extension

import net.minecraft.block.Material

/**
 * Generate a [Material] from a `String`.
 * @param material the string representing the material
 * @return The [Material] from the string's id.
 * @throws Exception if the material does not represent a (hardcoded) material.
 */
fun materialFromString(material: String): Material {
    return when (material) {
        "air" ->  Material.AIR
        "structure_void" -> Material.STRUCTURE_VOID
        "portal" -> Material.PORTAL
        "carpet" -> Material.CARPET
        "plant" -> Material.PLANT
        "underwater_plant" -> Material.UNDERWATER_PLANT
        "replaceable_plant" -> Material.REPLACEABLE_PLANT
        "nether_shoots" -> Material.NETHER_SHOOTS
        "replaceable_underwater_plant" -> Material.REPLACEABLE_UNDERWATER_PLANT
        "water" -> Material.WATER
        "bubble_column" -> Material.BUBBLE_COLUMN
        "lava" -> Material.LAVA
        "snow_layer" -> Material.SNOW_LAYER
        "fire" -> Material.FIRE
        "supported" -> Material.SUPPORTED
        "cobweb" -> Material.COBWEB
        "redstone_lamp" -> Material.REDSTONE_LAMP
        "organic_product" -> Material.ORGANIC_PRODUCT
        "soil" -> Material.SOIL
        "solid_organic" -> Material.SOLID_ORGANIC
        "dense_ice" -> Material.DENSE_ICE
        "aggregate" -> Material.AGGREGATE
        "sponge" -> Material.SPONGE
        "shulker_box" -> Material.SHULKER_BOX
        "wood" -> Material.WOOD
        "nether_wood" -> Material.NETHER_WOOD
        "bamboo_sapling" -> Material.BAMBOO_SAPLING
        "bamboo" -> Material.BAMBOO
        "wool" -> Material.WOOL
        "tnt" -> Material.TNT
        "leaves" -> Material.LEAVES
        "glass" -> Material.GLASS
        "ice" -> Material.ICE
        "cactus" -> Material.CACTUS
        "stone" -> Material.STONE
        "metal" -> Material.METAL
        "snow_block" -> Material.SNOW_BLOCK
        "repair_station" -> Material.REPAIR_STATION
        "barrier" -> Material.BARRIER
        "piston" -> Material.PISTON
        "unused_plant" -> Material.UNUSED_PLANT
        "gourd" -> Material.GOURD
        "egg" -> Material.EGG
        "cake" -> Material.CAKE
        else -> throw Exception("Unknown material $material")
    }
}