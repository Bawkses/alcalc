package data.entity

data class Character(
    override val name: String,
    override val health: Int,
    override val evasion: Int
) : AbsCharacter()

val TEST_CHARACTER = Character(
    name = "Test Char",
    health = 2000,
    evasion = 200
)
