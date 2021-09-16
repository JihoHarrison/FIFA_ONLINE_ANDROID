package kevin.android.fifaonline.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxkotlin.addTo
import kevin.android.fifaonline.*
import kevin.android.fifaonline.databinding.ActivityMainBinding
import kevin.android.fifaonline.repository.Repository
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel

        bindNameViewModel()
        bindLevelViewModel()

        binding.btnSearch.setOnClickListener {
            viewModel.getFifaInfo(binding.editNickName.text.toString())
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