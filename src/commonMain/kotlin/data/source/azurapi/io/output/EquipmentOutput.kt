package data.source.azurapi.io.output

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EquipmentOutput(
    @SerialName("id")
    val wikiId: String,
    @SerialName("wikiUrl")
    val wikiUrl: String,
    @SerialName("names")
    val name: TranslatedStringOutput,
    @SerialName("nationality")
    val faction: String,
    @SerialName("category")
    val category: String,
    @SerialName("image")
    val image: String,
    @SerialName("fits")
    val equipableOn: Map<String, String?>
)
