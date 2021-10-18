package kevin.android.fifaonline

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.model.UserModel
import kevin.android.fifaonline.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }

    var matchLists: MutableLiveData<List<MatchDTO>> = MutableLiveData<List<MatchDTO>>()
    private lateinit var lists: MutableList<MatchDTO>
    private lateinit var matchIdLists: List<String>

    private val fifaProcessor: BehaviorProcessor<UserModel> =
        BehaviorProcessor.create()
    private val matchIdProcessor: BehaviorProcessor<List<String>> =
        BehaviorProcessor.create()


    private var matchInfoProcessor: BehaviorProcessor<List<MatchDTO>> =
        BehaviorProcessor.create()

    var matchListsProcess: Flowable<List<MatchDTO>> = matchInfoProcessor
    val userNickName: Flowable<String> = fifaProcessor.map { it.nickname }
    val userLevel: Flowable<String> = fifaProcessor.map { it.level.toString() }
//    val matchInfoProcess: Single<List<MatchDTO>> = matchInfoProcessor
//    val user01: Flowable<String> = matchInfoProcessor.map { it.matchInfo[0].nickname }
//    val user02: Flowable<String> = matchInfoProcessor.map { it.matchInfo[1].nickname }
//    val user01Score: Flowable<Int> = matchInfoProcessor.map { it.matchInfo[0].shoot.goalTotal }
//    val user02Score: Flowable<Int> = matchInfoProcessor.map { it.matchInfo[1].shoot.goalTotal }
//    val user01Result: Flowable<String> = matchInfoProcessor.map { it.matchInfo[0].matchDetail.matchResult }
//    val user02Result: Flowable<String> = matchInfoProcessor.map { it.matchInfo[1].matchDetail.matchResult }

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
                    Log.d("matchIds", element)
                    getOfficialMatchInfo(element)
                }
            }, {
                Log.d("error", it.message.toString())
            }
        ).addToDisposables()
    }

    fun getOfficialMatchInfo(matchId: String) {

        repository.getMatchInfoRepo(matchId)
            .toObservable()
            .toSortedList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.toList() }
            .subscribe({
//                matchInfoProcessor.offer(it)
                //lists.add(it)
                //Log.d("matchIds", lists.toString())
                Log.d("helloworld", it.toString())
                //matchLists.value = it
                matchLists.postValue((matchLists.value ?: emptyList()) + it)
                //Log.d("error", it.matchInfo[0].nickname + " " + it.matchInfo[1].nickname)
                //Log.d("error", it.matchInfo[0].matchDetail.matchResult + " " + it.matchInfo[1].matchDetail.matchResult)

                //Log.d("error", it.message.toString())
            }, {

            }
            ).addToDisposables()

    }

    private fun Disposable.addToDisposables(): Disposable = addTo(disposables)
}