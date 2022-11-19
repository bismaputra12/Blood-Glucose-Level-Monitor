package bismaputra.bloodglucoselevelmonitor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import bismaputra.bloodglucoselevelmonitor.databinding.GlucoseLevelFragmentBinding
import bismaputra.bloodglucoselevelmonitor.databinding.ListItemGlucoseBinding
import java.util.*

class GlucoseHolder(
    private val binding: ListItemGlucoseBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(glucose: Glucose, onGlucoseClicked: (glucoseDate: Date) -> Unit) {
        binding.date.text = glucose.date.toString()
        binding.averageGlucose.text = averageGlucose(glucose).toString()

        binding.checkBox.isChecked = isNormal(glucose) == "normal"

        binding.root.setOnClickListener {
            onGlucoseClicked(glucose.date)
        }
    }

    private fun averageGlucose(glucose: Glucose): Int {
        var sum: Int = 0
        if (glucose.fasting != -1) {
            sum += glucose.fasting
        }
        if (glucose.breakfast != -1) {
            sum += glucose.breakfast
        }
        if (glucose.lunch != -1) {
            sum += glucose.lunch
        }
        if (glucose.dinner != -1) {
            sum += glucose.dinner
        }
        return sum / 4
    }

    private fun isNormal(glucose: Glucose): String {
        var string: String = ""
        if (glucose.fasting != -1 && glucose.breakfast != -1 && glucose.lunch != -1 && glucose.dinner != -1) {
            string = if (glucose.fasting in 70..99 && glucose.breakfast <= 140 && glucose.lunch <= 140 && glucose.dinner <= 140) {
                "normal"
            } else {
                "not normal"
            }
        }
        return string
    }
}


class GlucoseListAdapter(
    private val glucoses: List<Glucose>,
    private val onGlucoseClicked: (glucoseDate: Date) -> Unit
) : RecyclerView.Adapter<GlucoseHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlucoseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGlucoseBinding.inflate(inflater, parent, false)
        return GlucoseHolder(binding)
    }

    override fun onBindViewHolder(holder: GlucoseHolder, position: Int) {
        val glucose = glucoses[position]
        holder.bind(glucose, onGlucoseClicked)
    }

    override fun getItemCount() = glucoses.size
}