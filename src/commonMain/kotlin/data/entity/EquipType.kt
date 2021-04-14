package data.entity

sealed class EquipType {
    sealed class Gun : EquipType() {
        object Destroyer : Gun()
        object LightCruiser : Gun()
        object HeavyCruiser : Gun()
        object LargeCruiser : Gun()
        object Battleship : Gun()
        object Submarine : Gun()
    }

    sealed class Torpedo : EquipType() {
        object Default : Torpedo()
        object Submarine : Torpedo()
    }

    sealed class Plane : EquipType() {
        object Fighter : Plane()
        object DiveBomber : Plane()
        object TorpedoBomber : Plane()
        object Seaplane : Plane()
    }

    object AntiAir : EquipType()

    object Cargo : EquipType()

    object Auxiliary : EquipType()
}

fun EquipType.toDebugString(): String = when (this) {
    EquipType.Gun.Destroyer -> "Gun.Destroyer"
    EquipType.Gun.LightCruiser -> "Gun.LightCruiser"
    EquipType.Gun.HeavyCruiser -> "Gun.HeavyCruiser"
    EquipType.Gun.LargeCruiser -> "Gun.LargeCruiser"
    EquipType.Gun.Battleship -> "Gun.Battleship"
    EquipType.Gun.Submarine -> "Gun.Submarine"
    EquipType.Torpedo.Default -> "Torpedo.Default"
    EquipType.Torpedo.Submarine -> "Torpedo.Submarine"
    EquipType.Plane.Fighter -> "Plane.Fighter"
    EquipType.Plane.DiveBomber -> "Plane.DiveBomber"
    EquipType.Plane.TorpedoBomber -> "Plane.TorpedoBomber"
    EquipType.Plane.Seaplane -> "Plane.Seaplane"
    EquipType.AntiAir -> "AntiAir"
    EquipType.Cargo -> "Cargo"
    EquipType.Auxiliary -> "Auxiliary"
}