package kevin.android.fifaonline.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kevin.android.fifaonline.databinding.MatchResultBinding
import kevin.android.fifaonline.model.MatchDTO

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/09/21
 * Time: 10:56 오후
 */
class MatchResultAdapter(private val data: List<MatchDTO>) :
    RecyclerView.Adapter<MatchResultAdapter.MatchResultViewHolder>() {

    class MatchResultViewHolder(val binding: MatchResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MatchDTO) {
            binding.matchModel = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchResultViewHolder {
        val binding = MatchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchResultViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

}