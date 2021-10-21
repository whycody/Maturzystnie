package pl.whycody.maturzystnie.home.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.viewmodel.ext.android.viewModel
import pl.whycody.maturzystnie.databinding.FragmentFormBinding

class FormFragment : Fragment() {

    private val formViewModel: FormViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentFormBinding.inflate(inflater)
        observeSubjects()
        return binding.root
    }

    private fun observeSubjects() {
        formViewModel.getSubjects().observe(requireActivity(), {

        })
    }
}