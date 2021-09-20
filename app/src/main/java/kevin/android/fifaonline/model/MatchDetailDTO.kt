package kevin.android.fifaonline.model

import dagger.multibindings.IntoMap

data class MatchDetailDTO(
    val seasonId: Int,
    val matchResult: String, // 매치 결과 "승", "무", "패"
    val matchEndType: Int,
    val systemPause: Int,
    val foul: Int,
    val injury: Int,
    val redCards: Int,
    val yellowCards: Int,
    val dribble: Int,
    val cornerKick: Int,
    val possession: Int,
    val OffsideCount: Int,
    val averageRating: Double,
    val controller: String
)
