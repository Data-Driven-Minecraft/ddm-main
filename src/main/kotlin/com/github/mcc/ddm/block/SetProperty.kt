package com.github.mcc.ddm.block

import net.minecraft.state.property.Property
import java.util.*

class SetProperty(name: String, val values: List<String>): Property<String>(name, String::class.java) {
    override fun getValues(): MutableCollection<String> {
        return values.toMutableList()
    }

    override fun name(value: String): String {
        return value
    }

    override fun parse(name: String): Optional<String> {
        if (name in values) return Optional.of(name)
        return Optional.empty()
    }
}