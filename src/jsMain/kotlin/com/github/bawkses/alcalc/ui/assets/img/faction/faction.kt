package com.github.bawkses.alcalc.ui.assets.img.faction

import com.github.bawkses.alcalc.data.entity.Faction

private const val PATH_FACTION = "assets/img/faction"

val Faction.icon: String?
    get() = when (this) {
        Faction.SAKURA_EMPIRE ->
            "$PATH_FACTION/IJN_40px.png"
        Faction.EAGLE_UNION ->
            "$PATH_FACTION/USS_40px.png"
        Faction.ROYAL_NAVY ->
            "$PATH_FACTION/HMS_40px.png"
        Faction.IRON_BLOOD ->
            "$PATH_FACTION/KMS_40px.png"
        Faction.DRAGON_EMPERY ->
            "$PATH_FACTION/ROC_PLAN_40px.png"
        Faction.NORTHERN_PARLIAMENT ->
            "$PATH_FACTION/SN_40px.png"
        Faction.IRIS_LIBRE ->
            "$PATH_FACTION/FFNF_40px.png"
        Faction.VICHYA_DOMINION ->
            "$PATH_FACTION/MNF_40px.png"
        Faction.SARDEGNA_EMPIRE ->
            "$PATH_FACTION/RN_40x34px.png"
        Faction.OTHER ->
            null
    }
