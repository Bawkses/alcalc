package data.entity

import kotlin.reflect.KClass

data class CharaSlots(
    val first: Value,
    val second: Value,
    val third: Value,
    val fourth: Value,
    val fifth: Value
) : Iterable<CharaSlots.Value> {
    constructor(values: Array<Value>) : this(
        first = values[0],
        second = values[1],
        third = values[2],
        fourth = values[3],
        fifth = values[4]
    )

    data class Value(
        val canHold: Set<EquipType>,
        val efficiency: Int,
        val quantity: Int
    ) {
        fun <T : EquipType> canHold(other: KClass<T>) =
            canHold.any { other.isInstance(it) }

        fun canHold(other: EquipType) =
            canHold.any { it == other }
    }

    override fun iterator(): Iterator<Value> = iterator {
        yield(first); yield(second); yield(third); yield(fourth); yield(fifth)
    }

    fun <T : EquipType> canHold(other: KClass<T>) =
        any { it.canHold(other) }

    fun canHold(other: EquipType) =
        any { it.canHold(other) }
}
