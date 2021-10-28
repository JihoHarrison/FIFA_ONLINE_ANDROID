package kevin.android.fifaonline.api

import io.reactivex.Observable
import io.reactivex.Single
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.model.MatchIdDTO
import kevin.android.fifaonline.model.UserModel
import kevin.android.fifaonline.model.response.BaseResponse
import kevin.android.fifaonline.model.response.MaxDivisionDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("users")
    fun getUserInfo(
        @Query("nickname")
        nickname: String
    ): Single<UserModel>

    @GET("matches/{matchid}")
    fun getMatchInfo(
        @Path("matchid") matchid: String
    ): Observable<MatchDTO>

    @GET("users/{accessid}/matches")
    fun getOfficialMatchId(
        @Path("accessid") accessid : String,
        @Query("matchtype") matchtype : Int,
        @Query("offset") offset : Int,
        @Query("limit") limit : Int,
    ) : Single<List<String>>

    @GET("users/{accessid}/maxdivision")
    fun getMaxDivision(
        @Path("accessid") accessid: String
    ): Single<List<MaxDivisionDTO>>
}
