package pl.whycody.maturzystnie.home.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import pl.whycody.maturzystnie.databinding.FragmentFormBinding
import pl.whycody.maturzystnie.home.form.recycler.FormAdapter

class FormFragment : Fragment() {

    private val formViewModel: FormViewModel by viewModel()
    private lateinit var binding: FragmentFormBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFormBinding.inflate(inflater)
        binding.lifecycleOwner = activity
        binding.vm = formViewModel
        setupRecycler()
        observeCurrentMode()
        return binding.root
    }

    private fun setupRecycler() {
        val adapter = FormAdapter(formViewModel)
        binding.formRecycler.itemAnimator = null
        binding.formRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.formRecycler.adapter = adapter
        observeOptions(adapter)
    }

    private fun observeOptions(adapter: FormAdapter) {
        formViewModel.getOptions().observe(requireActivity(), {
            adapter.submitList(it.toMutableList())
        })
    }

    private fun observeCurrentMode() {
        formViewModel.getCurrentMode().observe(requireActivity(), {
            formViewModel.updateValues()
        })
    }
}