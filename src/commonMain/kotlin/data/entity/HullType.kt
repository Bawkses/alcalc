package data.entity

sealed class HullType {
    abstract val symbol: String
    abstract val defaultOrder: Int

    sealed class FrontLine : HullType() {
        object Destroyer : FrontLine() {
            override val symbol: String = "DD"
            override val defaultOrder: Int = 1
        }

        object LightCruiser : FrontLine() {
            override val symbol: String = "CL"
            override val defaultOrder: Int = 2
        }

        object HeavyCruiser : FrontLine() {
            override val symbol: String = "CA"
            override val defaultOrder: Int = 3
        }

        object LargeCruiser : FrontLine() {
            override val symbol: String = "CC"
            override val defaultOrder: Int = 4
        }

        object Repair : FrontLine() {
            override val symbol: String = "AR"
            override val defaultOrder: Int = 5
        }

        object MunitionShip : FrontLine() {
            override val symbol: String = "AE"
            override val defaultOrder: Int = 6
        }
    }

    sealed class BackLine : HullType() {
        object Monitor : BackLine() {
            override val symbol: String = "BM"
            override val defaultOrder: Int = 7
        }

        object Battlecruiser : BackLine() {
            override val symbol: String = "BC"
            override val defaultOrder: Int = 8
        }

        object Battleship : BackLine() {
            override val symbol: String = "BB"
            override val defaultOrder: Int = 9
        }

        object AviationBattleship : BackLine() {
            override val symbol: String = "ABB" // ??
            override val defaultOrder: Int = 10
        }

        object LightAircraftCarrier : BackLine() {
            override val symbol: String = "CVL"
            override val defaultOrder: Int = 11
        }

        object AircraftCarrier : BackLine() {
            override val symbol: String = "CV"
            override val defaultOrder: Int = 12
        }
    }

    sealed class Submarine : HullType() {
        object Default : Submarine() {
            override val symbol: String = "SS"
            override val defaultOrder: Int = 13
        }

        object AircraftCarrier : Submarine() {
            override val symbol: String = "SSV"
            override val defaultOrder: Int = 14
        }
    }

    operator fun compareTo(
        other: HullType
    ): Int = this.defaultOrder
        .compareTo(other.defaultOrder)
}
