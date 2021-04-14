package data.source.azurapi.io.output

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterOutput(
    @SerialName("retrofit")
    val hasRetrofit: Boolean = false,
    @SerialName("id")
    val wikiId: String,
    @SerialName("retrofitId")
    val wikiRetrofitId: String? = null,
    @SerialName("wikiUrl")
    val wikiUrl: String,
    @SerialName("names")
    val name: TranslatedStringOutput,
    @SerialName("nationality")
    val faction: String,
    @SerialName("hullType")
    val hullType: String,
    @SerialName("retrofitHullType")
    val hullTypeRetrofit: String? = null,
    @SerialName("class")
    val hullClass: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("rarity")
    val rarity: String,
    @SerialName("stats")
    val stats: Stats,
    @SerialName("slots")
    val slots: List<Slot>,
    @SerialName("skills")
    val skills: List<Skill>
) {
    @Serializable
    data class Stats(
        @SerialName("level120")
        val values: Values,
        @SerialName("level120Retrofit")
        val valuesAfterRetrofit: Values? = null
    ) {
        @Serializable
        data class Values(
            @SerialName("health")
            val health: Int,
            @SerialName("armor")
            val armorType: String,
            @SerialName("reload")
            val reload: Int,
            @SerialName("luck")
            val luck: Int,
            @SerialName("firepower")
            val firepower: Int,
            @SerialName("torpedo")
            val torpedo: Int,
            @SerialName("evasion")
            val evasion: Int,
            @SerialName("speed")
            val speed: Int,
            @SerialName("antiair")
            val antiAir: Int,
            @SerialName("aviation")
            val aviation: Int,
            @SerialName("oilConsumption")
            val oilCost: Int,
            @SerialName("accuracy")
            val accuracy: Int,
            @SerialName("antisubmarineWarfare")
            val antiSub: Int
        )
    }

    @Serializable
    data class Slot(
        @SerialName("type")
        val type: String,
        @SerialName("maxEfficiency")
        val efficiency: Int,
        @SerialName("kaiEfficiency")
        val efficiencyAfterRetrofit: Int? = null
    )

    @Serializable
    data class Skill(
        @SerialName("icon")
        val icon: String,
        @SerialName("names")
        val name: TranslatedStringOutput,
        @SerialName("description")
        val description: String
    )
}