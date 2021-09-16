package kevin.android.fifaonline

import android.util.Log

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/09/16
 * Time: 2:54 오후
 */
class ErrorLogger(private val message: String = "") : (Throwable) -> Unit {

    companion object {
        private const val TAG = "ErrorLogger"
    }

    override fun invoke(t: Throwable) {
        Log.e(TAG, message, t)
    }
}
