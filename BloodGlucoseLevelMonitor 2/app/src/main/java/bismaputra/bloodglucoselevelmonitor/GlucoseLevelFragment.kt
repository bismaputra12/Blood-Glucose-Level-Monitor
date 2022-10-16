package bismaputra.bloodglucoselevelmonitor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import bismaputra.bloodglucoselevelmonitor.databinding.GlucoseLevelFragmentBinding
import java.util.*

class GlucoseLevelFragment: Fragment(R.layout.glucose_level_fragment) {

    private var _binding: GlucoseLevelFragmentBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var glucose: Glucose
    private val glucoseLevelViewModel: GlucoseLevelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        glucose = Glucose(
            date = Date(),
            fasting = -1,
            breakfast = -1,
            lunch = -1,
            dinner = -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GlucoseLevelFragmentBinding.inflate(inflater, container, false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply{
            clearButton.setOnClickListener{
                clear()
            }
            fastingResult.apply {
                fastingEditText.doOnTextChanged { text, _, _, _ ->
                    glucose.fasting = Integer.valueOf(text.toString())
                    fastingResult.visibility = View.VISIBLE
                }
                text = glucose.fasting.toString()
            }
            breakfastResult.apply {
                breakfastEditText.doOnTextChanged { text, _, _, _ ->
                    glucose.breakfast = Integer.valueOf(text.toString())
                    breakfastResult.visibility = View.VISIBLE
                }
                text = glucose.breakfast.toString()
            }
            lunchResult.apply {
                lunchEditText.doOnTextChanged { text, _, _, _ ->
                    glucose.lunch = Integer.valueOf(text.toString())
                    lunchResult.visibility = View.VISIBLE
                }
                text = glucose.lunch.toString()
            }
            dinnerResult.apply {
                dinnerEditText.doOnTextChanged { text, _, _, _ ->
                    glucose.dinner = Integer.valueOf(text.toString())
                    dinnerResult.visibility = View.VISIBLE
                }
                text = glucose.dinner.toString()
            }
            if(fastingResult.visibility == View.VISIBLE &&
                breakfastResult.visibility == View.VISIBLE &&
                lunchResult.visibility == View.VISIBLE &&
                dinnerResult.visibility == View.VISIBLE){
                // check if the result is normal and set the result for the textview
                isNormalResult.apply {
                    text = glucoseLevelViewModel.isNormal(
                        glucose.fasting,
                        glucose.breakfast,
                        glucose.lunch,
                        glucose.dinner)
                    isNormalResult.visibility = View.VISIBLE
                }
                // make date textview visible
                dateResult.apply {
                    text = glucose.date.toString()
                    isEnabled = false
                    dateResult.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clear(){
        binding.apply{
            fastingEditText.text.clear()
            breakfastEditText.text.clear()
            lunchEditText.text.clear()
            dinnerEditText.text.clear()
            dateResult.visibility = View.INVISIBLE
            fastingResult.visibility = View.INVISIBLE
            breakfastResult.visibility = View.INVISIBLE
            lunchResult.visibility = View.INVISIBLE
            dinnerResult.visibility = View.INVISIBLE
            isNormalResult.visibility = View.INVISIBLE
        }
    }
}
