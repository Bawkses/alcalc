package data.entity

sealed class EquipType {
    sealed class Weapon : EquipType() {
        sealed class Gun : Weapon() {
            object Destroyer : Gun()
            object LightCruiser : Gun()
            object HeavyCruiser : Gun()
            object LargeCruiser : Gun()
            object Battleship : Gun()
            object Submarine : Gun()
        }

        sealed class Torpedo : Weapon() {
            object Default : Torpedo()
            object Submarine : Torpedo()
        }

        sealed class Plane : Weapon() {
            object Fighter : Plane()
            object DiveBomber : Plane()
            object TorpedoBomber : Plane()
            object Seaplane : Plane()
        }

        object AntiAir : Weapon()
    }

    object Cargo : EquipType()

    object Auxiliary : EquipType()
}

fun EquipType.toDebugString(): String = when (this) {
    EquipType.Weapon.Gun.Destroyer -> "Weapon.Gun.Destroyer"
    EquipType.Weapon.Gun.LightCruiser -> "Weapon.Gun.LightCruiser"
    EquipType.Weapon.Gun.HeavyCruiser -> "Weapon.Gun.HeavyCruiser"
    EquipType.Weapon.Gun.LargeCruiser -> "Weapon.Gun.LargeCruiser"
    EquipType.Weapon.Gun.Battleship -> "Weapon.Gun.Battleship"
    EquipType.Weapon.Gun.Submarine -> "Weapon.Gun.Submarine"
    EquipType.Weapon.Torpedo.Default -> "Weapon.Torpedo.Default"
    EquipType.Weapon.Torpedo.Submarine -> "Weapon.Torpedo.Submarine"
    EquipType.Weapon.Plane.Fighter -> "Weapon.Plane.Fighter"
    EquipType.Weapon.Plane.DiveBomber -> "Weapon.Plane.DiveBomber"
    EquipType.Weapon.Plane.TorpedoBomber -> "Weapon.Plane.TorpedoBomber"
    EquipType.Weapon.Plane.Seaplane -> "Weapon.Plane.Seaplane"
    EquipType.Weapon.AntiAir -> "Weapon.AntiAir"
    EquipType.Cargo -> "Cargo"
    EquipType.Auxiliary -> "Auxiliary"
}
