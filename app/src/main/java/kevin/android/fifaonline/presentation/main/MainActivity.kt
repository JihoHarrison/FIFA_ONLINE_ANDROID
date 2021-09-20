package kevin.android.fifaonline.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
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

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        /** viewModel과 View 묶어주는 부분 **/
        bindNameViewModel()
        bindLevelViewModel()

        /** 검색 버튼 클릭 대신 안드로이드 소프트 키보드의 완료 버튼을 통해서 검색 수행 **/
        binding.editNickName.setOnEditorActionListener { _, actionId, _ ->
            var handler = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.getFifaInfo(binding.editNickName.text.toString())
                handler = true
            }
            handler
        }

        /** 여기는 그냥 검색 버튼을 이용해서 검색 수행하는 부분 **/
        binding.btnSearch.setOnClickListener {
            // two way binding
            // text watcher
            viewModel.getFifaInfo(binding.editNickName.text.toString())
        }
    }

    fun bindEditText(nickname: String) {

    }

    private fun bindNameViewModel() {
        viewModel.userNickName.observeOnMain().subscribe({
            binding.txtNickName.text = it
        }, {
            binding.txtNickName.text = "이름을 가져올 수 없습니다."
        })
    }

    private fun bindLevelViewModel() {
        viewModel.userLevel.observeOnMain().subscribe({
            binding.txtLevel.text = it
        }, {
            binding.txtLevel.text = "레벨을 가져올 수 없습니다."
        })
    }



}