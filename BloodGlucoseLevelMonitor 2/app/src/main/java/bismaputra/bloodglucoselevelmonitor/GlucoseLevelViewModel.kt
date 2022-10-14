package bismaputra.bloodglucoselevelmonitor

import androidx.lifecycle.ViewModel

class GlucoseLevelViewModel: ViewModel() {

    fun isNormal(fastingLevel: Int, breakfastLevel: Int, lunchLevel: Int, dinnerLevel: Int): String{
        return if (fastingLevel in 70..99 && breakfastLevel <= 140 && lunchLevel <= 140 && dinnerLevel <= 140){
            "Normal"
        } else {
            "Abnormal"
        }
    }
}