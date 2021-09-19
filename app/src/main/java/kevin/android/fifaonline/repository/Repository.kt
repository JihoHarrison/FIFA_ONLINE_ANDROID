package kevin.android.fifaonline.repository

import io.reactivex.Single
import kevin.android.fifaonline.api.Api
import kevin.android.fifaonline.model.Model
import javax.inject.Inject

// repository를 요래 쓰는게 맞나..?
class Repository @Inject constructor(private val api: Api) {

    fun getModel(nickname: String): Single<Model> = api.getUserInfo(nickname)


}