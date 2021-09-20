package kevin.android.fifaonline.model

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/09/20
 * Time: 4:19 오후
 */
data class MatchDTO(
    val matchId : String,
    val matchDate : String,
    val matchType: Int,
    val matchInfo : List<MatchInfoDTO>
)
data class MatchInfoDTO(
    val accessId : String,
    val nickname : String,
    val matchDetail : MatchDetailDTO,
    val shoot : ShootDTO,
    val shootDetail : List<ShootDetailDTO>,
    val pass : PassDTO,
    val defence : DefenceDTO,
    val player : List<PlayerDTO>
)
