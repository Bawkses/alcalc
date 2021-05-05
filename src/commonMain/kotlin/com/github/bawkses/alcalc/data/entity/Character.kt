package com.github.bawkses.alcalc.data.entity

data class Character(
    val wikiId: String,
    val wikiUrl: String,
    val hullName: String,
    val hullClass: String,
    val hullType: HullType,
    val faction: Faction,
    val isRetrofitted: Boolean,
    val slots: CharaSlots,
    override val health: Int,
    override val evasion: Int,
    override val accuracy: Int,
    override val reload: Int,
    override val firepower: Int,
    override val torpedo: Int,
    override val aviation: Int,
    override val antiAir: Int,
    override val antiSub: Int,
    override val luck: Int,
    override val speed: Int
) : CharaStats
