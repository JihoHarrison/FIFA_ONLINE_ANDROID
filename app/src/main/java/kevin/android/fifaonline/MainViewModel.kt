package kevin.android.fifaonline

import android.service.autofill.FieldClassification
import android.service.autofill.Transformation
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer
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
import io.reactivex.subjects.BehaviorSubject.*
import io.reactivex.subjects.ReplaySubject
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.model.MatchIdDTO
import kevin.android.fifaonline.model.UserModel
import kevin.android.fifaonline.repository.CoroutineRepository
import kevin.android.fifaonline.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: CoroutineRepository) : ViewModel() {

    //val matchLists = MutableLiveData<ArrayList<MatchDTO>>()
    val matchLists = MutableLiveData<List<MatchDTO>>()
    val emptyList : MutableLiveData<List<MatchDTO>> = Transformations.map(matchLists) {
        it.sortedBy { it.matchDate }
    } as MutableLiveData<List<MatchDTO>>
    val userModel = MutableLiveData<UserModel>()
    private lateinit var matchIdLists: List<String>

    fun getUserModel(nickName: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getModel(nickName).apply {
                    userModel.postValue(this)
                    getMatchId(this.accessId)
                }
                withContext(Dispatchers.Main) {
                    userModel.value = userModel.value
                }
            }

        } catch (e: Throwable) {
            Log.e("ERROR_VIEWMODEL", "!!!!!ERROR ERROR ERROR ERROR!!!!!")
        }
    }

    fun getMatchId(accessId: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getOfficialMatchIdRepo(accessId).apply {
                    for (element in this) {
                        getMatchInfo(element)
                    }
                }
            }
        } catch (e: Throwable) {
            Log.e("ERROR_VIEWMODEL", "!!!!!ERROR ERROR ERROR ERROR!!!!!")

        }
    }

    fun getMatchInfo(matchId: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getMatchInfoRepo(matchId).apply {
                    //matchLists.value?.add(this)
                    matchLists.postValue((matchLists.value ?: emptyList()) + this)
                    Log.d("ERROR_VIEWMODEL", this.toString())
                }
            }

        } catch (e: Throwable) {
            Log.e("ERROR_VIEWMODEL", "!!!!!ERROR ERROR ERROR ERROR!!!!!")

        }
    }


}