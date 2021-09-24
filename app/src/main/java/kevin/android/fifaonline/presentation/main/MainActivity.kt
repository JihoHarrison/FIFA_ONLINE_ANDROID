package kevin.android.fifaonline.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import kevin.android.fifaonline.*
import kevin.android.fifaonline.adapter.MatchResultAdapter
import kevin.android.fifaonline.databinding.ActivityMainBinding
import kevin.android.fifaonline.presentation.detail.MatchDetailFragment
import kevin.android.fifaonline.presentation.match.MatchActivity


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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
                startActivity(Intent(this@MainActivity, MatchActivity::class.java))
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
            startActivity(Intent(this, MatchActivity::class.java))
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