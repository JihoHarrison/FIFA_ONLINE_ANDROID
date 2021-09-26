package kevin.android.fifaonline.presentation.match

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.FieldClassification
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import kevin.android.fifaonline.MainViewModel
import kevin.android.fifaonline.R
import kevin.android.fifaonline.adapter.MatchResultAdapter
import kevin.android.fifaonline.databinding.ActivityMainBinding
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
            ),MatchInfoDTO(
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
            )
        )

        var matchs: List<MatchDTO> = listOf(MatchDTO("he", "he", 1, matchInfos),
            MatchDTO("he", "he", 1, matchInfos)
            )
        //Log.d("hey", viewModel.getOfficialMatchInfo("60e46576eba5bd4548925819").toString())
        //Log.d("hey", viewModel.getOfficialMatchInfo("60be3e8681e90119c911f687").toString())
        //Log.d("hey", viewModel.getFifaInfo("꼬솜슈터").toString())

        viewModel.matchInfo

        viewModel.matchInfo.observeOnMain().subscribe(
            {
                Log.d("hey", it.get(0).toString())
                adapter = MatchResultAdapter(it)
                binding.rcMatchList.adapter =  adapter
            },{
                Log.d("hey", it.message.toString())
            }

        )





    }


}