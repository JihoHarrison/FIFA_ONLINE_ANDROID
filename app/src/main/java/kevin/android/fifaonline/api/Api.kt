package kevin.android.fifaonline.api

import io.reactivex.Single
import kevin.android.fifaonline.model.Model
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("users")
    fun getUserInfo(
        @Query("nickname")
        nickname: String
    ): Single<Model>

}
