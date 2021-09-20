package kevin.android.fifaonline.model

data class ShootDetailDTO(
    val goalTime: Int,
    val spId: Int, // 슈팅 선수 고유 식별자
    val assistSpId: Int, // 어시스트 선수 고유 식별자
)
