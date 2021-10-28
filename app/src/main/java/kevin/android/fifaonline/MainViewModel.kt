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

    private val fifaProcessor: BehaviorProcessor<UserModel> =
        BehaviorProcessor.create()

    private val matchIdProcessor: BehaviorProcessor<List<String>> =
        BehaviorProcessor.create()

    private val loadingProcessor: BehaviorProcessor<Boolean> = BehaviorProcessor.createDefault(true)
    val isLoading = loadingProcessor

    private val maxDivisionProcessor: BehaviorProcessor<String> = BehaviorProcessor.createDefault("검색 중 . . .")
    val userMaxDivision = maxDivisionProcessor

    private val setErrorMessageProcessor: BehaviorProcessor<String> =
        BehaviorProcessor.create()


    val setErrorMessage: Flowable<String> = setErrorMessageProcessor
    val userNickName: Flowable<String> = fifaProcessor.map { it.nickname }
    val userLevel: Flowable<String> = fifaProcessor.map { it.level.toString() }

    fun getFifaInfo(nickname: String) {
        repository.getModel(nickname).subscribeOn(Schedulers.io()).observeOnMain().subscribe(
            {
                fifaProcessor.offer(it)
                maxDivisionProcessor.offer(" ")
                getOfficialMatchId(it.accessId)
                getUserMaxDivision(it.accessId)
            }, {
                Log.d("error", it.message.toString())
            }
        ).addToDisposables()
    }

    fun getOfficialMatchId(accessId: String) {
        repository.getOfficialMatchIdRepo(accessId).subscribeOn(Schedulers.io()).observeOnMain()
            .subscribe(
                {
                    matchIdProcessor.offer(it)
                    it.forEach { element ->
                        getOfficialMatchInfo(element)
                    }
                }, {
                    Log.d("error", it.message.toString())
                }
            ).addToDisposables()
    }

    fun getOfficialMatchInfo(matchId: String) {

        repository.getMatchInfoRepo(matchId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingProcessor.offer(true) }
            .doOnComplete { loadingProcessor.offer(false) }
            .subscribe({
                matchLists.setValue((matchLists.value ?: emptyList()) + it)
            }, {
                setErrorMessageProcessor.offer("매치 정보 조회 실패!")
            }
            ).addToDisposables()
    }

    fun getUserMaxDivision(accessId: String) {
        repository.getUserMaxDivision(accessId).subscribeOn(Schedulers.io()).observeOnMain()
            .subscribeWithErrorLogger {
                it.forEach {
                    if (it.matchType == 50) {
                        when (it.division) {
                            800 -> maxDivisionProcessor.offer("슈퍼챔피언스")
                            900 -> maxDivisionProcessor.offer("챔피언스")
                            1000 -> maxDivisionProcessor.offer("슈퍼챌린지")
                            1100 -> maxDivisionProcessor.offer("챌린지1")
                            1200 -> maxDivisionProcessor.offer("챌린지2")
                            1300 -> maxDivisionProcessor.offer("챌린지3")
                            2000 -> maxDivisionProcessor.offer("월드클래스1")
                            2100 -> maxDivisionProcessor.offer("월드클래스2")
                            2200 -> maxDivisionProcessor.offer("월드클래스3")
                            2300 -> maxDivisionProcessor.offer("프로1")
                            2400 -> maxDivisionProcessor.offer("프로2")
                        }
                    }
                }
            }
            .addToDisposables()
    }

    private fun Disposable.addToDisposables(): Disposable = addTo(disposables)
}