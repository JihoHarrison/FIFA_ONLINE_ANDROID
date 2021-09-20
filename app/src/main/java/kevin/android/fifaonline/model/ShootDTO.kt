package kevin.android.fifaonline.model

data class ShootDTO(
    val shootTotal: Int,
    val effectiveShootTotal: Int,
    val shootOutScore: Int,
    val goalTotal: Int, // 총 골 수
    val goalTotalDisplay: Int,
    val ownGoal: Int,
    val shootHeading: Int,
    val goalHeading: Int,
    val shootFreekick: Int,
    val goalFreekick: Int,
    val shootPenaltyKick: Int,
    val goalPenaltyKick: Int
)
