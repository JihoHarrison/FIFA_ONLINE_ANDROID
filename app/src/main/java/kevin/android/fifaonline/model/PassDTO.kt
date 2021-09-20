package kevin.android.fifaonline.model

data class PassDTO(
    val passTry: Int,
    val passSuccess: Int,
    val shortPassTry: Int,
    val shortPassSuccess: Int,
    val longPassTry: Int,
    val longPassSuccess: Int,
    val throughPassTry: Int,
    val throughPassSuccess: Int,
    val lobbedThroughPassTry: Int,
    val lobbedThroughPassSuccess: Int

)
