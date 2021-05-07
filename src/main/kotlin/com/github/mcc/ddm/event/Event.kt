package com.github.mcc.ddm.event

import net.minecraft.util.Identifier

/**
 * Represents an event.
 * It can be triggered by any of the [triggers] firing; or by a command invoking it manually.
 * When it's triggered, all [listener][listeners] functions will be invoked.
 * @property triggers a list of [EventTrigger]s that will trigger this event.
 */
class Event(val triggers: List<EventTrigger>, val listeners: List<Identifier>) {
    companion object {
        @JvmStatic
        fun empty(): Event = Event(listOf(), listOf())
    }
}