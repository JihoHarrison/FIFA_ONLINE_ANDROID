package kevin.android.fifaonline

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kevin.android.fifaonline.util.UserPref

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/09/16
 * Time: 10:46 오전
 */
@HiltAndroidApp
class FifaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initPref()
    }

    private fun initPref(){
        UserPref.init(applicationContext)
    }




}