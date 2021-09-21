package kevin.android.fifaonline.model.response

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/09/22
 * Time: 1:09 오전
 */
data class BaseResponse<T>(
    val items : List<T>
)
