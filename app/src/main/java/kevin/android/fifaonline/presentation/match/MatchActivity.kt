package kevin.android.fifaonline.presentation.match

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kevin.android.fifaonline.MainViewModel
import kevin.android.fifaonline.R
import kevin.android.fifaonline.adapter.MatchResultAdapter
import kevin.android.fifaonline.databinding.ActivityMatchBinding
import kevin.android.fifaonline.model.*
import kevin.android.fifaonline.observeOnMain
import kevin.android.fifaonline.subscribeWithErrorLogger

@AndroidEntryPoint
class MatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MatchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match)

        viewModel.getFifaInfo("꼬솜슈터")
        viewModel.isLoading.observeOnMain()
            .subscribeWithErrorLogger {
                binding.loadingBar.isVisible = it
            }
            .addTo(CompositeDisposable())
        viewModel.matchLists.observe(this, Observer {

            adapter = MatchResultAdapter(it.sortedByDescending { it.matchDate })
            binding.rcMatchList.adapter = adapter
        })
    }

}