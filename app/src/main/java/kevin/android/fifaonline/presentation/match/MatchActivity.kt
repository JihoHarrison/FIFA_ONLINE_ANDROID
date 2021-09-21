package kevin.android.fifaonline.presentation.match

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kevin.android.fifaonline.MainViewModel
import kevin.android.fifaonline.R
import kevin.android.fifaonline.adapter.MatchResultAdapter
import kevin.android.fifaonline.databinding.ActivityMainBinding
import kevin.android.fifaonline.databinding.ActivityMatchBinding
import kevin.android.fifaonline.model.MatchDTO
import kevin.android.fifaonline.observeOnMain

@AndroidEntryPoint
class MatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchBinding
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match)
        Log.d("error", viewModel.matchList.toString() + "sdasdkfjaslkdfjq")
        Log.d("error", viewModel.matchInfo.toString() + "sdasdkfjaslkdfjq")

        viewModel.matchInfo.observeOnMain()
            .subscribe(
                {
                    Log.d("error", viewModel.matchList.toString() + "sdasdkfjaslkdfjq")

                    binding.rcMatchList.adapter = MatchResultAdapter(it)

                },{
                    Log.d("error", "오류입니다")
                }
            )
    }



}