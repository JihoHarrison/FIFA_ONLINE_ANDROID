package kevin.android.fifaonline
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.model.MatchIdDTO
import kevin.android.fifaonline.model.UserModel
import kevin.android.fifaonline.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }

    private val fifaProcessor: BehaviorProcessor<UserModel> =
        BehaviorProcessor.create()
    private val matchIdProcessor: BehaviorProcessor<List<String>> =
        BehaviorProcessor.create()
    private val matchInfoProcessor: BehaviorProcessor<MatchDTO> =
        BehaviorProcessor.create()
    val matchInfo = matchInfoProcessor

    val userNickName: Flowable<String> = fifaProcessor.map { it.nickname }
    val userLevel: Flowable<String> = fifaProcessor.map { it.level.toString() }
    val userAccessId: Flowable<String> = fifaProcessor.map { it.accessId }

    fun getFifaInfo(nickname: String) {
        repository.getModel(nickname).subscribeOn(Schedulers.io()).observeOnMain().subscribe(
            {
                fifaProcessor.offer(it)
                getOfficialMatchId(it.accessId)
            }, {
                Log.d("error", it.message.toString())
            }
        ).addToDisposables()
    }

    fun getOfficialMatchId(accessId: String) {
        repository.getOfficialMatchIdRepo(accessId).subscribeOn(Schedulers.io()).observeOnMain().subscribe(
            {
                matchIdProcessor.offer(it)
                for (element in it) {
                    getOfficialMatchInfo(element)
                }
            }, {
                Log.d("error", it.message.toString())
            }
        ).addToDisposables()
    }

    fun getOfficialMatchInfo(matchId: String) {
        repository.getMatchInfoRepo(matchId).subscribeOn(Schedulers.io()).observeOnMain().subscribe(
            {
                matchInfoProcessor.offer(it)
                Log.d("error", it.matchInfo[0].nickname + " " + it.matchInfo[1].nickname)
            }, {
                Log.d("error", it.message.toString())
            }
        ).addToDisposables()
    }

    private fun Disposable.addToDisposables(): Disposable = addTo(disposables)
}