package bismaputra.bloodglucoselevelmonitor

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import bismaputra.bloodglucoselevelmonitor.databinding.FragmentGlucoseListBinding

private const val TAG = "CrimeListFragment"

class GlucoseListFragment: Fragment() {
    private var _binding: FragmentGlucoseListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val glucoseListViewModel: GlucoseListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total glucoses: ${glucoseListViewModel.glucoses.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlucoseListBinding.inflate(inflater,container,false)
        //create a layout manager
        binding.glucoseRecyclerView.layoutManager = LinearLayoutManager(context)
        //connect the fragment with the adapter
        val glucoses = glucoseListViewModel.glucoses
        val adapter = GlucoseListAdapter(glucoses)
        binding.glucoseRecyclerView.adapter = adapter
        //return the root tog create the view
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}