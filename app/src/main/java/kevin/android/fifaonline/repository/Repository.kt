package kevin.android.fifaonline.repository

import io.reactivex.Single
import kevin.android.fifaonline.api.Api
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.model.MatchIdDTO
import kevin.android.fifaonline.model.UserModel
import javax.inject.Inject

// repository를 요래 쓰는게 맞나..?
class Repository @Inject constructor(private val api: Api) {

    fun getModel(nickname: String): Single<UserModel> = api.getUserInfo(nickname)
    fun getOfficialMatchIdRepo(accessId : String) : Single<List<String>> // 공식 경기
        = api.getOfficialMatchId(accessId, matchtype = 50, offset = 0, limit = 100)
    fun getMatchInfoRepo(matchId : String) : Single<MatchDTO> = api.getMatchInfo(matchId).flatMap {
        Single.just(it)
    }
    //fun getMatchIdRepo() : Single<MatchIdDTO> = api.getMatchInfo()


}