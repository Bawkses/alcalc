package data.entity

sealed class HullType {
    abstract val symbol: String

    sealed class FrontLine : HullType() {
        object Destroyer : FrontLine() {
            override val symbol: String = "DD"
        }

        object LightCruiser : FrontLine() {
            override val symbol: String = "CL"
        }

        object HeavyCruiser : FrontLine() {
            override val symbol: String = "CA"
        }

        object LargeCruiser : FrontLine() {
            override val symbol: String = "CC"
        }

        object Repair : FrontLine() {
            override val symbol: String = "AR"
        }

        object MunitionShip : FrontLine() {
            override val symbol: String = "AE"
        }
    }

    sealed class BackLine : HullType() {
        object Monitor : BackLine() {
            override val symbol: String = "BM"
        }

        object Battlecruiser : BackLine() {
            override val symbol: String = "BC"
        }

        object Battleship : BackLine() {
            override val symbol: String = "BB"
        }

        object AviationBattleship : BackLine() {
            override val symbol: String = "BB" // ??
        }

        object LightAircraftCarrier : BackLine() {
            override val symbol: String = "CVL"
        }

        object AircraftCarrier : BackLine() {
            override val symbol: String = "CV"
        }
    }

    sealed class Submarine : HullType() {
        object Default : Submarine() {
            override val symbol: String = "SS"
        }

        object AircraftCarrier : Submarine() {
            override val symbol: String = "SSV"
        }
    }
}