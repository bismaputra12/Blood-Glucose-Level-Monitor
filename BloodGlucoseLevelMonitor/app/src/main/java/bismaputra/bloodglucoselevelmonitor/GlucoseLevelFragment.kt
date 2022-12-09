package bismaputra.bloodglucoselevelmonitor

import android.content.Intent
import android.os.Bundle
import android.view.*
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
import java.lang.String.format
import java.text.DateFormat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
                    if (Integer.valueOf(text.toString()) in 70..99) {
                        isNormalResult.text = "ISNORMAL: TRUE"
                    } else {
                        isNormalResult.text = "ISNORMAL: FALSE"
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
                    if (Integer.valueOf(text.toString()) <= 140) {
                        isNormalResult.text = "ISNORMAL: TRUE"
                    } else {
                        isNormalResult.text = "ISNORMAL: FALSE"
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
                        if (Integer.valueOf(text.toString()) <= 140) {
                            isNormalResult.text = "ISNORMAL: TRUE"
                        } else {
                            isNormalResult.text = "ISNORMAL: FALSE"
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
                        if (Integer.valueOf(text.toString()) <= 140) {
                            isNormalResult.text = "ISNORMAL: TRUE"
                        } else {
                            isNormalResult.text = "ISNORMAL: FALSE"
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.glucose_level_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.send_glucose -> {
                val reportIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, getGlucoseReport())
                    putExtra(
                        Intent.EXTRA_SUBJECT,
                        getString(R.string.glucose_report_subject)
                    )
                }
                val chooserIntent = Intent.createChooser(
                    reportIntent,
                    getString(R.string.send_report)
                )
                startActivity(chooserIntent)
                true
            }
            R.id.delete_glucose -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    glucoseLevelViewModel.deleteGlucose(args.glucoseDate)
                    findNavController().popBackStack()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
            val df: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
            dateButton.text = df.format(glucose.date)
            dateResult.text = glucose.date.toString()
            dateResult.visibility = View.VISIBLE
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

    private fun getGlucoseReport(): String {
        binding.apply {
            val date = dateResult.text.toString()
            val fasting = fastingResult.text.toString()
            val breakfast = breakfastResult.text.toString()
            val lunch = lunchResult.text.toString()
            val dinner = dinnerResult.text.toString()
            val isNormal = isNormalResult.text.toString()

            return getString(
                R.string.glucose_report,
                date,
                fasting,
                breakfast,
                lunch,
                dinner,
                isNormal
            )
        }
    }
}


