package kevin.android.fifaonline.repository

import io.reactivex.Single
import kevin.android.fifaonline.api.CoroutineApi
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.model.UserModel
import javax.inject.Inject

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/10/18
 * Time: 5:05 오후
 */
class CoroutineRepository @Inject constructor(private val coroutineApi: CoroutineApi) {
    suspend fun getModel(nickname: String): UserModel = coroutineApi.getUserInfo(nickname)
    suspend fun getOfficialMatchIdRepo(accessId : String) : List<String> // 공식 경기
            = coroutineApi.getOfficialMatchId(accessId, matchtype = 50, offset = 0, limit = 100)
    suspend fun getMatchInfoRepo(matchId : String) : MatchDTO = coroutineApi.getMatchInfo(matchId)
    //fun getMatchIdRepo() : Single<MatchIdDTO> = api.getMatchInfo()


}