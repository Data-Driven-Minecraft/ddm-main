package com.github.mcc.ddm.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItem
import net.minecraft.state.StateManager

class CustomBlock(private val data: BlockDataEntry): Block(data.settings) {

    init {
        //var state = stateManager.defaultState
        //for (default in data.states) {
            //state = default.value.apply(state)
        //}
        //defaultState = state
    }

    var item: BlockItem? = null
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        //for (property in data.states) {
        //    builder.add(property.value.property)
        //}
        super.appendProperties(builder)
    }
}