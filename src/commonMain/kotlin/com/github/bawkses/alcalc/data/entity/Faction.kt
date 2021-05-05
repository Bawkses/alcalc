package com.github.bawkses.alcalc.data.entity

@Suppress("SpellCheckingInspection")
enum class Faction {
    SAKURA_EMPIRE,
    EAGLE_UNION,
    ROYAL_NAVY,
    IRON_BLOOD,
    DRAGON_EMPERY,
    NORTHERN_PARLIAMENT,
    IRIS_LIBRE,
    VICHYA_DOMINION,
    SARDEGNA_EMPIRE,
    OTHER;

    val nameCapitalized = this
        .name
        .toLowerCase()
        .split('_')
        .asSequence()
        .map { it.capitalize() }
        .reduce { acc, s -> "$acc $s" }
}
