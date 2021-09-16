package kevin.android.fifaonline

import android.util.Log
import android.view.Display
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kevin.android.fifaonline.model.Model
import kevin.android.fifaonline.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val disposables by lazy { CompositeDisposable() }

    private val fifaProcessor: BehaviorProcessor<Model> =
        BehaviorProcessor.create()
    val userNickName : Flowable<String> = fifaProcessor.map { it.nickname }
    val userLevel: Flowable<String> = fifaProcessor.map { it.level.toString() }
    //    fun fifaRepository(nickname: String) {
//        repository.getModel(nickname).subscribeOn(Schedulers.io())
//            .observeOnMain()
//            .subscribe(
//                {
//                    it.nickname
//                    it.level
//                    it?.let { fifaProcessor.offer(it) }
//                }, {
//                    it.message
//                }
//            ).addToDisposables()
//    }
    fun getFifaInfo(nickname : String) {
        repository.getModel(nickname).subscribeOn(Schedulers.io()).observeOnMain().subscribe(
            {
            fifaProcessor.offer(it)
            },{
                Log.d("error", it.message.toString())
            }
        )
    }


    fun Disposable.addToDisposables(): Disposable = addTo(disposables)


}