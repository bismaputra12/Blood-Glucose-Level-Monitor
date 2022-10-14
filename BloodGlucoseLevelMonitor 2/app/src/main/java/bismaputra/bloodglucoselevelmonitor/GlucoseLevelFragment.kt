package bismaputra.bloodglucoselevelmonitor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import bismaputra.bloodglucoselevelmonitor.databinding.GlucoseLevelFragmentBinding

class GlucoseLevelFragment: Fragment(R.layout.glucose_level_fragment) {

    private var _binding: GlucoseLevelFragmentBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var glucose: Glucose
    private val glucoseLevelViewModel: GlucoseLevelViewModel by viewModels()

    private var fastingLevel: Int = 0
    private var breakfastLevel: Int = 0
    private var lunchLevel: Int = 0
    private var dinnerLevel: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            if(fastingEditText.text.toString() != "" || breakfastEditText.text.toString() != ""
                || lunchEditText.text.toString() != "" || dinnerEditText.text.toString() != "")
            {
                dateResult.text = glucose.date.toString()
                fastingResult.text = fastingEditText.text.toString()
                breakfastResult.text = breakfastEditText.text.toString()
                lunchResult.text = lunchEditText.text.toString()
                dinnerResult.text = dinnerEditText.text.toString()
                fastingLevel = Integer.valueOf(fastingEditText.text.toString())
                breakfastLevel = Integer.valueOf(breakfastEditText.text.toString())
                lunchLevel = Integer.valueOf(lunchEditText.text.toString())
                dinnerLevel = Integer.valueOf(dinnerEditText.text.toString())
                isNormalResult.text = glucoseLevelViewModel.isNormal(fastingLevel, breakfastLevel, lunchLevel, dinnerLevel)
                resultVisible()
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

    private fun resultVisible(){
        binding.apply{
            dateResult.visibility = View.VISIBLE
            fastingResult.visibility = View.VISIBLE
            breakfastResult.visibility = View.VISIBLE
            lunchResult.visibility = View.VISIBLE
            dinnerResult.visibility = View.VISIBLE
            isNormalResult.visibility = View.VISIBLE
        }
    }
}
