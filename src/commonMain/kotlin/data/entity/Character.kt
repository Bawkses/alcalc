package data.entity

data class Character(
    val wikiId: String,
    val wikiUrl: String,
    val hullName: String,
    val hullClass: String,
    val hullType: HullType,
    override val health: Int,
    override val evasion: Int,
    override val accuracy: Int,
    override val luck: Int
): CharaStats()
