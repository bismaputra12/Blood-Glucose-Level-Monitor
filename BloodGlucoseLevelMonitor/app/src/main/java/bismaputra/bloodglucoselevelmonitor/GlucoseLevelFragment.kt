package bismaputra.bloodglucoselevelmonitor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import bismaputra.bloodglucoselevelmonitor.databinding.FragmentGlucoseListBinding
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
                glucoseLevelViewModel.updateGlucose { oldGlucose ->
                    oldGlucose.copy(fasting = Integer.valueOf(text.toString()))
                }
                fastingResult.text = text.toString()
                fastingResult.visibility = View.VISIBLE
            }
            breakfastEditText.doOnTextChanged { text, _, _, _ ->
                glucoseLevelViewModel.updateGlucose { oldGlucose ->
                    oldGlucose.copy(breakfast = Integer.valueOf(text.toString()))
                }
                breakfastResult.text = text.toString()
                breakfastResult.visibility = View.VISIBLE
            }
            lunchEditText.doOnTextChanged { text, _, _, _ ->
                glucoseLevelViewModel.updateGlucose { oldGlucose ->
                    oldGlucose.copy(lunch = Integer.valueOf(text.toString()))
                }
                lunchResult.text = text.toString()
                lunchResult.visibility = View.VISIBLE
            }
            dinnerEditText.doOnTextChanged { text, _, _, _ ->
                glucoseLevelViewModel.updateGlucose { oldGlucose ->
                    oldGlucose.copy(dinner = Integer.valueOf(text.toString()))
                }
                dinnerResult.text = text.toString()
                dinnerResult.visibility = View.VISIBLE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                glucoseLevelViewModel.glucose.collect { glucose ->
                    glucose?.let { updateUi(it) }
                }
            }
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

    private fun isNormal(glucose: Glucose, binding: GlucoseLevelFragmentBinding) {
        if (glucose.fasting != -1 && glucose.breakfast != -1 && glucose.lunch != -1 && glucose.dinner != -1) {
            if (glucose.fasting in 70..99 && glucose.breakfast <= 140 && glucose.lunch <= 140 && glucose.dinner <= 140) {
                binding.isNormalResult.text = "ISNORMAL: TRUE"
            } else {
                binding.isNormalResult.text = "ISNORMAL: FALSE"
            }
            binding.isNormalResult.visibility = View.VISIBLE
            //show date textview
            binding.dateResult.text = glucose.date.toString()
            binding.dateResult.visibility = View.VISIBLE
        }
    }

    private fun updateUi(glucose: Glucose) {
        binding.apply {
            if (fastingEditText.text.toString() != glucose.fasting.toString()) {
                fastingEditText.setText(glucose.fasting)
                fastingResult.visibility = View.VISIBLE
                fastingResult.text = glucose.fasting.toString()
                isNormal(glucose, binding)
            }
            if (breakfastEditText.text.toString() != glucose.breakfast.toString()) {
                breakfastEditText.setText(glucose.breakfast)
                breakfastResult.visibility = View.VISIBLE
                breakfastResult.text = glucose.fasting.toString()
                isNormal(glucose, binding)
            }
            if (lunchEditText.text.toString() != glucose.lunch.toString()) {
                lunchEditText.setText(glucose.lunch)
                lunchResult.visibility = View.VISIBLE
                lunchResult.text = glucose.fasting.toString()
                isNormal(glucose, binding)
            }
            if (dinnerEditText.text.toString() != glucose.dinner.toString()) {
                dinnerEditText.setText(glucose.dinner)
                dinnerResult.visibility = View.VISIBLE
                dinnerResult.text = glucose.fasting.toString()
                isNormal(glucose, binding)
            }
        }
    }
}

