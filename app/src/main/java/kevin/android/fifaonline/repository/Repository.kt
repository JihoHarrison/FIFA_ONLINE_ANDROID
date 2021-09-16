package kevin.android.fifaonline.repository

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kevin.android.fifaonline.BaseResponse
import kevin.android.fifaonline.api.Api
import kevin.android.fifaonline.model.Model
import kevin.android.fifaonline.api.Client
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

// repository를 요래 쓰는게 맞나..?
class Repository @Inject constructor(private val api: Api) {

    fun getModel(nickname: String): Single<Model> = api.getUserInfo(nickname)

}