package kevin.android.fifaonline.presentation.match

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kevin.android.fifaonline.MainViewModel
import kevin.android.fifaonline.R
import kevin.android.fifaonline.adapter.MatchResultAdapter
import kevin.android.fifaonline.databinding.ActivityMatchBinding
import kevin.android.fifaonline.model.*

@AndroidEntryPoint
class MatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MatchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match)

        var shootDetail: List<ShootDetailDTO> = listOf(ShootDetailDTO(1, 1, 1))
        var player: List<PlayerDTO> = listOf(PlayerDTO(1, 1, 1))

        var matchInfos: List<MatchInfoDTO> = listOf(
            MatchInfoDTO(
                "qewr",
                "jiho",
                MatchDetailDTO(
                    2021,
                    "패", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.1,
                    "hello"
                ),
                ShootDTO(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                shootDetail,
                PassDTO(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                DefenceDTO(1, 1, 2, 1),
                player
            ), MatchInfoDTO(
                "qewr",
                "kevin",
                MatchDetailDTO(
                    2021,
                    "승", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.1,
                    "hello"
                ),
                ShootDTO(1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1),
                shootDetail,
                PassDTO(1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
                DefenceDTO(1, 1, 2, 1),
                player
            )
        )

        var matchs: List<MatchDTO> = listOf(
            MatchDTO("he", "he", 1, matchInfos),
            MatchDTO("he", "he", 1, matchInfos),
            MatchDTO("he", "he", 1, matchInfos),
            MatchDTO("he", "he", 1, matchInfos),
            MatchDTO("he", "he", 1, matchInfos),
        )
        //Log.d("hey", viewModel.getOfficialMatchInfo("60e46576eba5bd4548925819").toString())
        //Log.d("hey", viewModel.getOfficialMatchInfo("60be3e8681e90119c911f687").toString())
        //Log.d("hey", viewModel.getFifaInfo("꼬솜슈터").toString())

//        adapter = MatchResultAdapter(matchs)
//        binding.rcMatchList.adapter =  adapter
        Log.d("test", viewModel.getOfficialMatchInfo("61163b421af40e3e7c0a7f37").toString())
        Log.d("test", viewModel.getOfficialMatchInfo("61163b421af40e3e7c0a7f37").toString())
        Log.d("test", viewModel.getOfficialMatchInfo("60d43cc1c74b881cdf5c39f8").toString())
        viewModel.matchLists.observe(this, Observer
        {
            adapter = MatchResultAdapter(matchs)
            binding.rcMatchList.adapter = adapter
        }
        )
    }
}