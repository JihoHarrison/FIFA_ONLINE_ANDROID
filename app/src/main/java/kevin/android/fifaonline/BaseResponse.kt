package kevin.android.fifaonline

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/09/16
 * Time: 1:42 오후
 */
interface BaseResponse<T> {
    fun onSuccess(data: T)

    fun onFail(description: String?)

    fun onError(t: Throwable)

    fun onLoading()

    fun onLoaded()
}