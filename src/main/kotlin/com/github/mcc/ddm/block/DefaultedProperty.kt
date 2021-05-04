package com.github.mcc.ddm.block

import net.minecraft.block.BlockState
import net.minecraft.state.property.Property

/**
 * Holds a property and its default
 */
data class DefaultedProperty<T: Comparable<T>, V: T>(val property: Property<T>, val default: V) {
    fun apply(state: BlockState): BlockState {
        return state.with(property, default)
    }
}