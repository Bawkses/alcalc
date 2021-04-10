package data.source.azurapi.io.output

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class NationOutput {
    @SerialName("Sakura Empire")
    SAKURA_EMPIRE,
    @SerialName("Eagle Union")
    EAGLE_UNION,
    @SerialName("Royal Navy")
    ROYAL_NAVY,
    @SerialName("Iron Blood")
    IRON_BLOOD,
    @SerialName("Dragon Empery")
    DRAGON_EMPERY,
    @SerialName("Northern Parliament")
    NORTHERN_PARLIAMENT,
    @SerialName("Iris Libre")
    IRIS_LIBRE,
    @SerialName("Vichya Dominion")
    VICHYA__DOMINION,
    @SerialName("Sardegna Empire")
    SARDEGNA_EMPIRE
}