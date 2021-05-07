package com.github.mcc.ddm.event

import net.minecraft.loot.condition.LootCondition
import net.minecraft.util.Identifier

/**
 * Represents a trigger for an [Event].
 * @property source An [Identifier] representing the source event trigger
 * @property conditions A list of [LootCondition]s that have to succeed to trigger this event.
 */
data class EventTrigger(val source: Identifier, val conditions: List<LootCondition>)