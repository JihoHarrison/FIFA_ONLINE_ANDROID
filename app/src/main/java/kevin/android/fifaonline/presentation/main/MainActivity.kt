package kevin.android.fifaonline.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxkotlin.addTo
import kevin.android.fifaonline.*
import kevin.android.fifaonline.databinding.ActivityMainBinding
import kevin.android.fifaonline.presentation.detail.MatchDetailFragment
import kevin.android.fifaonline.presentation.match.MatchFragment
import kevin.android.fifaonline.repository.Repository
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private var matchFragment = MatchFragment()
    private var matchDetailFragment = MatchDetailFragment()
    private lateinit var txtNickName : String

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel
        txtNickName = binding.editNickName.text.toString()

        bindNameViewModel()
        bindLevelViewModel()
        bindEditText(txtNickName)



//        binding.btnSearch.setOnClickListener {
//            // two way binding
//            // text watcher
//
//        }
    }

    fun throwNickName(bundle: Bundle){
        bundle.putString("nickName", txtNickName)
    }

    fun bindEditText(nickname : String){
        binding.editNickName.setOnEditorActionListener { v, actionId, event ->
            var handler = false
            if(actionId == EditorInfo.IME_ACTION_DONE){
                viewModel.getFifaInfo(nickname)

                handler = true
            }
            handler
        }
    }

    fun bindNameViewModel() {
        viewModel.userNickName.observeOnMain().subscribe({
            binding.txtNickName.text = it
        }, {
            binding.txtNickName.text = "이름을 가져올 수 없습니다."
        })
    }

    fun bindLevelViewModel() {
        viewModel.userLevel.observeOnMain().subscribe({
            binding.txtLevel.text = it
        }, {
            binding.txtLevel.text = "레벨을 가져올 수 없습니다."
        })
    }
}