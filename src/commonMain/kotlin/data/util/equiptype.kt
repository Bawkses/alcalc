package data.util

import data.entity.CharaStats
import data.entity.Character
import data.entity.EquipType

/** @return The appropriate stat for the given weapon */
fun statForEquipment(
    equipType: EquipType.Weapon,
    charaStats: CharaStats
) = when (equipType) {
    is EquipType.Weapon.Gun ->
        charaStats.firepower
    is EquipType.Weapon.Torpedo ->
        charaStats.torpedo
    is EquipType.Weapon.Plane ->
        charaStats.aviation
    is EquipType.Weapon.AntiAir ->
        charaStats.antiAir
}

/**
 * Calculate the damage multiplier the given values would have.
 * Not actually useful for damage calculation, but as a gauge of potential damage.
 */
fun statMultiplier(
    equipType: EquipType.Weapon,
    statValue: Int,
    efficiency: Int,
    quantity: Int,
) = ((statValue / 100f) * (efficiency) / 100f) * when (equipType) {
    // Torpedoes in the same slot are shot/reloaded sequentially,
    // so assuming they are shot when reloaded, quantity is irrelevant
    is EquipType.Weapon.Torpedo ->
        1
    else ->
        quantity
}

/** @see statMultiplier */
fun Character.statMultiplierFor(
    equipType: EquipType.Weapon
) = this
    .slots
    .filter { it.canHold(equipType) }
    .map {
        statMultiplier(
            equipType = equipType,
            statValue = statForEquipment(equipType, this),
            efficiency = it.efficiency,
            quantity = it.quantity
        )
    }
    .sum()
