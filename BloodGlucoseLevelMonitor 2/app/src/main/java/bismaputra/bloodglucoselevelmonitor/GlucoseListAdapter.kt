package bismaputra.bloodglucoselevelmonitor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import bismaputra.bloodglucoselevelmonitor.databinding.ListItemGlucoseBinding

class GlucoseHolder(
    private val binding: ListItemGlucoseBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(glucose: Glucose) {
        binding.date.text = glucose.date.toString()
        binding.averageGlucose.text = (90..150).random().toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${glucose.date}\n" +
                        "Fasting = ${glucose.fasting}\n" +
                        "Breakfast = ${glucose.breakfast}\n" +
                        "Lunch = ${glucose.lunch}\n" +
                        "Dinner = ${glucose.dinner}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}


class GlucoseListAdapter(
    private val glucoses: List<Glucose>
) : RecyclerView.Adapter<GlucoseHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlucoseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGlucoseBinding.inflate(inflater, parent, false)
        return GlucoseHolder(binding)
    }

    override fun onBindViewHolder(holder: GlucoseHolder, position: Int) {
        val glucose = glucoses[position]
        holder.bind(glucose)
    }

    override fun getItemCount() = glucoses.size
}