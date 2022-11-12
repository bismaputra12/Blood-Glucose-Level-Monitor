package bismaputra.bloodglucoselevelmonitor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class GlucoseListViewModel: ViewModel() {
    private val glucoseRepository = GlucoseRepository.get()

    private val _glucoses: MutableStateFlow<List<Glucose>> =
        MutableStateFlow(emptyList())

    val glucoses: StateFlow<List<Glucose>>
        get() = _glucoses.asStateFlow()

    //create the glucose objects and add them to the list
    init {
        viewModelScope.launch {
            glucoseRepository.getGlucoses().collect {
                _glucoses.value = it
            }
        }
    }
}