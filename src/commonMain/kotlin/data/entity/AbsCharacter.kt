package data.entity

abstract class AbsCharacter {
    abstract val name: String
    abstract val health: Int
    abstract val evasion: Int

    open val effectiveHealth
        get() = health * evasion // todo
}
