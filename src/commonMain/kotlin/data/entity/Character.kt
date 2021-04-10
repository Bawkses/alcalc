package data.entity

data class Character(
    val wikiId: String,
    val wikiUrl: String,
    val hullName: String,
    val hullClass: String,
    val hullType: HullType,
    val health: Int,
    val evasion: Int
) {

    val effectiveHealth
        get() = health * evasion // todo
}
