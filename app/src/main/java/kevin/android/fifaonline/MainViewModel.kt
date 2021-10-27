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
import java.util.concurrent.TimeUnit
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

    val userNickName: Flowable<String> = fifaProcessor.map { it.nickname }
    val userLevel: Flowable<String> = fifaProcessor.map { it.level.toString() }

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

                matchLists.setValue((matchLists.value?.sortedByDescending { it.matchDate }
                    ?: emptyList()) + it)
                matchLists.value?.sortedByDescending { it.matchDate }
            }, {

            }
            ).addToDisposables()
    }

    private fun Disposable.addToDisposables(): Disposable = addTo(disposables)
}