package bismaputra.bloodglucoselevelmonitor

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import bismaputra.bloodglucoselevelmonitor.databinding.GlucoseLevelFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class GlucoseLevelViewModel(glucoseDate: Date) : ViewModel(){
    private val glucoseRepository = GlucoseRepository.get()

    private val _glucose: MutableStateFlow<Glucose?> = MutableStateFlow(null)
    val glucose: StateFlow<Glucose?> = _glucose.asStateFlow()

    init {
        viewModelScope.launch{
            _glucose.value = glucoseRepository.getGlucose(glucoseDate)
        }
    }

    fun updateGlucose(onUpdate: (Glucose) -> Glucose) {
        _glucose.update { oldGlucose ->
            oldGlucose?.let { onUpdate(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()

        glucose.value?.let { glucoseRepository.updateGlucose(it) }
    }

    fun isNormal(glucose: Glucose, binding: GlucoseLevelFragmentBinding) {
        if (glucose.fasting != -1 && glucose.breakfast != -1 && glucose.lunch != -1 && glucose.dinner != -1) {
            if (glucose.fasting < 70 || glucose.fasting > 99) {
                binding.fastingEditText.setTextColor(Color.RED)
                binding.dateResult.setTextColor(Color.RED)
                binding.fastingResult.setTextColor(Color.RED)
                binding.breakfastResult.setTextColor(Color.RED)
                binding.lunchResult.setTextColor(Color.RED)
                binding.dinnerResult.setTextColor(Color.RED)
                binding.isNormalResult.setTextColor(Color.RED)
                binding.isNormalResult.text = "ISNORMAL: FALSE"
            } else if (glucose.breakfast > 140) {
                binding.breakfastEditText.setTextColor(Color.RED)
                binding.dateResult.setTextColor(Color.RED)
                binding.fastingResult.setTextColor(Color.RED)
                binding.breakfastResult.setTextColor(Color.RED)
                binding.lunchResult.setTextColor(Color.RED)
                binding.dinnerResult.setTextColor(Color.RED)
                binding.isNormalResult.setTextColor(Color.RED)
                binding.isNormalResult.text = "ISNORMAL: FALSE"
            } else if (glucose.lunch > 140) {
                binding.lunchEditText.setTextColor(Color.RED)
                binding.dateResult.setTextColor(Color.RED)
                binding.fastingResult.setTextColor(Color.RED)
                binding.breakfastResult.setTextColor(Color.RED)
                binding.lunchResult.setTextColor(Color.RED)
                binding.dinnerResult.setTextColor(Color.RED)
                binding.isNormalResult.setTextColor(Color.RED)
                binding.isNormalResult.text = "ISNORMAL: FALSE"
            } else if (glucose.dinner > 140) {
                binding.dinnerEditText.setTextColor(Color.RED)
                binding.dateResult.setTextColor(Color.RED)
                binding.fastingResult.setTextColor(Color.RED)
                binding.breakfastResult.setTextColor(Color.RED)
                binding.lunchResult.setTextColor(Color.RED)
                binding.dinnerResult.setTextColor(Color.RED)
                binding.isNormalResult.setTextColor(Color.RED)
                binding.isNormalResult.text = "ISNORMAL: FALSE"
            } else if (glucose.fasting in 70..99 && glucose.breakfast <= 140 && glucose.lunch <= 140 && glucose.dinner <= 140) {
                binding.fastingEditText.setTextColor(Color.BLACK)
                binding.dateResult.setTextColor(Color.BLACK)
                binding.fastingResult.setTextColor(Color.BLACK)
                binding.breakfastResult.setTextColor(Color.BLACK)
                binding.lunchResult.setTextColor(Color.BLACK)
                binding.dinnerResult.setTextColor(Color.BLACK)
                binding.isNormalResult.setTextColor(Color.BLACK)
                binding.isNormalResult.text = "ISNORMAL: TRUE"
            }
            binding.isNormalResult.visibility = View.VISIBLE
        }
    }
}

class GlucoseLevelViewModelFactory(
    private val glucoseDate: Date
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GlucoseLevelViewModel(glucoseDate) as T
    }
}
