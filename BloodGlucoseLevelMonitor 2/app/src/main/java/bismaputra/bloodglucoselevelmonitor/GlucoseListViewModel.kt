package bismaputra.bloodglucoselevelmonitor

import androidx.lifecycle.ViewModel
import java.util.*

class GlucoseListViewModel: ViewModel() {

    val glucoses = mutableListOf<Glucose>()
    val calendar = GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH))

    //create the glucose objects and add them to the list
    init{
        for (i in 0..99) {
            val glucoseObject = Glucose(date = calendar.time,
                fasting = (60..180).random(),
                breakfast = (60..180).random(),
                lunch = (60..180).random(),
                dinner = (60..180).random())
            calendar.add(Calendar.DATE, -1)
            glucoses.add(glucoseObject)
        }
    }
}