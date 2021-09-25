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
import kevin.android.fifaonline.model.*
import kevin.android.fifaonline.observeOnMain

@AndroidEntryPoint
class MatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: MatchResultAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_match)

        var shootDetail : List<ShootDetailDTO> = listOf(ShootDetailDTO(1,1,1))

        var matchInfos : List<MatchInfoDTO> = listOf(MatchInfoDTO("qewr", "jiho", MatchDetailDTO(2021,
            "패",0,0,0,0,0,0,0,0,0,0,0.1
"hello"), ShootDTO(1,1,1,1,1,1,1,1,1,1,1,1)
        , shootDetail, DefenceDTO()
        ))

        var lists : List<MatchDTO> = listOf(MatchDTO("asd", "12:29", 50, MatchInfoDTO()))


        adapter = MatchResultAdapter()

        binding.rcMatchList.adapter = adapter





    }



}