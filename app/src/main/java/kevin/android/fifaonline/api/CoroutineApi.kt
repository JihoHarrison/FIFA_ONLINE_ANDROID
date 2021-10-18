package kevin.android.fifaonline.api

import io.reactivex.Observable
import io.reactivex.Single
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.model.MatchIdDTO
import kevin.android.fifaonline.model.UserModel
import kevin.android.fifaonline.model.response.BaseResponse
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoroutineApi {

    @GET("users")
    suspend fun getUserInfo(
        @Query("nickname")
        nickname: String
    ): UserModel

    @GET("matches/{matchid}")
    suspend fun getMatchInfo(
        @Path("matchid") matchid: String
    ): MatchDTO

    @GET("users/{accessid}/matches")
    suspend fun getOfficialMatchId(
        @Path("accessid") accessid : String,
        @Query("matchtype") matchtype : Int,
        @Query("offset") offset : Int,
        @Query("limit") limit : Int,
    ) : List<String>
}
