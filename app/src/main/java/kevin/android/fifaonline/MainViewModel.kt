package kevin.android.fifaonline

import android.service.autofill.FieldClassification
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.BehaviorSubject.create
import io.reactivex.subjects.BehaviorSubject.createDefault
import io.reactivex.subjects.ReplaySubject
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.model.MatchIdDTO
import kevin.android.fifaonline.model.UserModel
import kevin.android.fifaonline.repository.Repository
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val disposables by lazy { CompositeDisposable() }

    var matchLists: MutableLiveData<List<MatchDTO>> = MutableLiveData<List<MatchDTO>>()

    private val fifaProcessor: BehaviorProcessor<UserModel> =
        BehaviorProcessor.create()
    private val matchIdProcessor: BehaviorProcessor<List<String>> =
        BehaviorProcessor.create()

    //val matchIdList = MutableLiveData<List<String>>()
    private var matchInfoProcessor: ReplaySubject<List<MatchDTO>> =
        ReplaySubject.create()

    var matchListsProcess: Observable<List<MatchDTO>> = matchInfoProcessor
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
                    getOfficialMatchInfo(element)
                    //Log.d("helloworld", getOfficialMatchInfo(element).toString())
                }
                //Log.d("hellofriend",getOfficialMatchInfo(it[0]).toString())
            }, {
                Log.d("error", it.message.toString())
            }
        ).addToDisposables()
    }

    fun getOfficialMatchInfo(matchId: String) {

        repository.getMatchInfoRepo(matchId)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.toList() }
            .subscribe ({
                Log.d("helloworld", it.toString())
                matchLists.postValue((matchLists.value?: emptyList())+it)
                //Log.d("error", it.matchInfo[0].nickname + " " + it.matchInfo[1].nickname)
                //Log.d("error", it.matchInfo[0].matchDetail.matchResult + " " + it.matchInfo[1].matchDetail.matchResult)

                //Log.d("error", it.message.toString())
            },{

            }
            ).addToDisposables()

    }

    private fun Disposable.addToDisposables(): Disposable = addTo(disposables)
}