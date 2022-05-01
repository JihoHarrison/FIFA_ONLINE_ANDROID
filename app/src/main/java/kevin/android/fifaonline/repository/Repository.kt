package kevin.android.fifaonline.repository

import io.reactivex.Observable
import io.reactivex.Single
import kevin.android.fifaonline.api.Api
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.model.UserModel
import kevin.android.fifaonline.model.response.MaxDivisionDTO
import javax.inject.Inject


class Repository @Inject constructor(private val api: Api) {

    fun getModel(nickname: String): Single<UserModel> = api.getUserInfo(nickname)
    fun getOfficialMatchIdRepo(accessId: String): Single<List<String>> // 공식 경기
            = api.getOfficialMatchId(accessId, matchtype = 50, offset = 0, limit = 20)

    fun getMatchInfoRepo(matchId: String): Observable<MatchDTO> = api.getMatchInfo(matchId)
    fun getUserMaxDivision(accessId: String): Single<List<MaxDivisionDTO>> = api.getMaxDivision(accessId)
}