package bismaputra.bloodglucoselevelmonitor

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bismaputra.bloodglucoselevelmonitor.databinding.GlucoseLevelFragmentBinding
import kotlinx.coroutines.launch
import java.util.*

class GlucoseLevelFragment: Fragment(R.layout.glucose_level_fragment) {

    private var _binding: GlucoseLevelFragmentBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    // this is to take the glucose data from the list fragment
    private val args: GlucoseLevelFragmentArgs by navArgs()

    //pass the glucose data to GlucoseLevelViewModel
    private val glucoseLevelViewModel: GlucoseLevelViewModel by viewModels {
        GlucoseLevelViewModelFactory(args.glucoseDate)
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

        binding.apply {
            clearButton.setOnClickListener {
                clear()
            }
            historyButton.setOnClickListener {
                it.findNavController().popBackStack()
            }
            fastingEditText.doOnTextChanged { text, _, _, _ ->
                if (text.toString() != "") {
                    glucoseLevelViewModel.updateGlucose { oldGlucose ->
                        oldGlucose.copy(fasting = Integer.valueOf(text.toString()))
                    }
                    fastingResult.text = text.toString()
                    fastingResult.visibility = View.VISIBLE
                    if (Integer.valueOf(text.toString()) in 70..99){
                        isNormalResult.text = "ISNORMAL: NORMAL"
                        fastingEditText.setTextColor(Color.BLACK)
                        dateResult.setTextColor(Color.BLACK)
                        fastingResult.setTextColor(Color.BLACK)
                        breakfastResult.setTextColor(Color.BLACK)
                        lunchResult.setTextColor(Color.BLACK)
                        dinnerResult.setTextColor(Color.BLACK)
                        isNormalResult.setTextColor(Color.BLACK)
                    } else {
                        isNormalResult.text = "ISNORMAL: ABNORMAL"
                        fastingEditText.setTextColor(Color.RED)
                        dateResult.setTextColor(Color.RED)
                        fastingResult.setTextColor(Color.RED)
                        breakfastResult.setTextColor(Color.RED)
                        lunchResult.setTextColor(Color.RED)
                        dinnerResult.setTextColor(Color.RED)
                        isNormalResult.setTextColor(Color.RED)
                    }
                } else {
                    fastingResult.visibility = View.INVISIBLE
                }
            }
            breakfastEditText.doOnTextChanged { text, _, _, _ ->
                if (text.toString() != "") {
                    glucoseLevelViewModel.updateGlucose { oldGlucose ->
                        oldGlucose.copy(breakfast = Integer.valueOf(text.toString()))
                    }
                    breakfastResult.text = text.toString()
                    breakfastResult.visibility = View.VISIBLE
                    if (Integer.valueOf(text.toString()) <= 140){
                        isNormalResult.text = "ISNORMAL: NORMAL"
                        breakfastEditText.setTextColor(Color.BLACK)
                        dateResult.setTextColor(Color.BLACK)
                        fastingResult.setTextColor(Color.BLACK)
                        breakfastResult.setTextColor(Color.BLACK)
                        lunchResult.setTextColor(Color.BLACK)
                        dinnerResult.setTextColor(Color.BLACK)
                        isNormalResult.setTextColor(Color.BLACK)
                    } else {
                        isNormalResult.text = "ISNORMAL: ABNORMAL"
                        breakfastEditText.setTextColor(Color.RED)
                        dateResult.setTextColor(Color.RED)
                        fastingResult.setTextColor(Color.RED)
                        breakfastResult.setTextColor(Color.RED)
                        lunchResult.setTextColor(Color.RED)
                        dinnerResult.setTextColor(Color.RED)
                        isNormalResult.setTextColor(Color.RED)
                    }
                } else {
                    breakfastResult.visibility = View.INVISIBLE
                }
                lunchEditText.doOnTextChanged { text, _, _, _ ->
                    if (text.toString() != "") {
                        glucoseLevelViewModel.updateGlucose { oldGlucose ->
                            oldGlucose.copy(lunch = Integer.valueOf(text.toString()))
                        }
                        lunchResult.text = text.toString()
                        lunchResult.visibility = View.VISIBLE
                        if (Integer.valueOf(text.toString()) <= 140){
                            isNormalResult.text = "ISNORMAL: NORMAL"
                            lunchEditText.setTextColor(Color.BLACK)
                            dateResult.setTextColor(Color.BLACK)
                            fastingResult.setTextColor(Color.BLACK)
                            breakfastResult.setTextColor(Color.BLACK)
                            lunchResult.setTextColor(Color.BLACK)
                            dinnerResult.setTextColor(Color.BLACK)
                            isNormalResult.setTextColor(Color.BLACK)
                        } else {
                            isNormalResult.text = "ISNORMAL: ABNORMAL"
                            lunchEditText.setTextColor(Color.RED)
                            dateResult.setTextColor(Color.RED)
                            fastingResult.setTextColor(Color.RED)
                            breakfastResult.setTextColor(Color.RED)
                            lunchResult.setTextColor(Color.RED)
                            dinnerResult.setTextColor(Color.RED)
                            isNormalResult.setTextColor(Color.RED)
                        }
                    } else {
                        lunchResult.visibility = View.INVISIBLE
                    }
                }
                dinnerEditText.doOnTextChanged { text, _, _, _ ->
                    if (text.toString() != "") {
                        glucoseLevelViewModel.updateGlucose { oldGlucose ->
                            oldGlucose.copy(dinner = Integer.valueOf(text.toString()))
                        }
                        dinnerResult.text = text.toString()
                        dinnerResult.visibility = View.VISIBLE
                        if (Integer.valueOf(text.toString()) <= 140){
                            isNormalResult.text = "ISNORMAL: NORMAL"
                            dinnerEditText.setTextColor(Color.BLACK)
                            dateResult.setTextColor(Color.BLACK)
                            fastingResult.setTextColor(Color.BLACK)
                            breakfastResult.setTextColor(Color.BLACK)
                            lunchResult.setTextColor(Color.BLACK)
                            dinnerResult.setTextColor(Color.BLACK)
                            isNormalResult.setTextColor(Color.BLACK)
                        } else {
                            isNormalResult.text = "ISNORMAL: ABNORMAL"
                            dinnerEditText.setTextColor(Color.RED)
                            dateResult.setTextColor(Color.RED)
                            fastingResult.setTextColor(Color.RED)
                            breakfastResult.setTextColor(Color.RED)
                            lunchResult.setTextColor(Color.RED)
                            dinnerResult.setTextColor(Color.RED)
                            isNormalResult.setTextColor(Color.RED)
                        }
                    } else {
                        dinnerResult.visibility = View.INVISIBLE
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                glucoseLevelViewModel.glucose.collect { glucose ->
                    glucose?.let { updateUi(it) }
                }
            }
        }

        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { _, bundle ->
            val newDate =
                bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            glucoseLevelViewModel.updateGlucose { it.copy(date = newDate) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clear() {
        binding.apply {
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

    private fun updateUi(glucose: Glucose) {
        binding.apply {
            dateButton.text = glucose.date.toString()
            if (dateButton.text.toString() == glucose.date.toString()){
                dateResult.text = glucose.date.toString()
                dateResult.visibility = View.VISIBLE
            }
            dateButton.setOnClickListener {
                findNavController().navigate(
                    GlucoseLevelFragmentDirections.selectDate(glucose.date)
                )
            }
            if (fastingEditText.text.toString() != glucose.fasting.toString()) {
                fastingEditText.setText(glucose.fasting.toString())
                fastingResult.visibility = View.VISIBLE
                fastingResult.text = glucose.fasting.toString()
                glucoseLevelViewModel.isNormal(glucose, binding)
            }
            if (breakfastEditText.text.toString() != glucose.breakfast.toString()) {
                breakfastEditText.setText(glucose.breakfast.toString())
                breakfastResult.visibility = View.VISIBLE
                breakfastResult.text = glucose.breakfast.toString()
                glucoseLevelViewModel.isNormal(glucose, binding)
            }
            if (lunchEditText.text.toString() != glucose.lunch.toString()) {
                lunchEditText.setText(glucose.lunch.toString())
                lunchResult.visibility = View.VISIBLE
                lunchResult.text = glucose.lunch.toString()
                glucoseLevelViewModel.isNormal(glucose, binding)
            }
            if (dinnerEditText.text.toString() != glucose.dinner.toString()) {
                dinnerEditText.setText(glucose.dinner.toString())
                dinnerResult.visibility = View.VISIBLE
                dinnerResult.text = glucose.dinner.toString()
                glucoseLevelViewModel.isNormal(glucose, binding)
            }
        }
    }
}


