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
        _glucose.update{ oldGlucose ->
            oldGlucose?.let{onUpdate(it)}
        }
    }

    suspend fun deleteGlucose(glucoseDate: Date){
        glucoseRepository.deleteGlucose(glucoseDate)
    }

    override fun onCleared() {
        super.onCleared()

        glucose.value?.let { glucoseRepository.updateGlucose(it) }
    }

    fun isNormal(glucose: Glucose, binding: GlucoseLevelFragmentBinding) {
        if (glucose.fasting != -1 && glucose.breakfast != -1 && glucose.lunch != -1 && glucose.dinner != -1) {
            if (glucose.fasting < 70 || glucose.fasting > 99) {
                binding.isNormalResult.text = "ISNORMAL: FALSE"
            } else if (glucose.breakfast > 140) {
                binding.isNormalResult.text = "ISNORMAL: FALSE"
            } else if (glucose.lunch > 140) {
                binding.isNormalResult.text = "ISNORMAL: FALSE"
            } else if (glucose.dinner > 140) {
                binding.isNormalResult.text = "ISNORMAL: FALSE"
            } else if (glucose.fasting in 70..99 && glucose.breakfast <= 140 && glucose.lunch <= 140 && glucose.dinner <= 140) {
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
