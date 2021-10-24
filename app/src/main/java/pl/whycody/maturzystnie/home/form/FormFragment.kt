package pl.whycody.maturzystnie.home.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import pl.whycody.maturzystnie.R
import pl.whycody.maturzystnie.databinding.FragmentFormBinding
import pl.whycody.maturzystnie.home.form.recycler.FormAdapter
import pl.whycody.maturzystnie.home.form.recycler.FormItemDecoration

class FormFragment : Fragment() {

    private var lastMode = 0
    private var showNextPanel = true
    private val formViewModel: FormViewModel by viewModel()
    private lateinit var binding: FragmentFormBinding
    private lateinit var centerToLeftAnim: Animation
    private lateinit var centerToRightAnim: Animation
    private lateinit var rightToCenterAnim: Animation
    private lateinit var leftToCenterAnim: Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFormBinding.inflate(inflater)
        binding.lifecycleOwner = activity
        binding.vm = formViewModel
        declareAnimations()
        setupRecycler()
        observeCurrentMode()
        return binding.root
    }

    private fun declareAnimations() {
        centerToLeftAnim = AnimationUtils.loadAnimation(context, R.anim.center_to_left_anim)
        centerToRightAnim = AnimationUtils.loadAnimation(context, R.anim.center_to_right_anim)
        rightToCenterAnim = AnimationUtils.loadAnimation(context, R.anim.right_to_center_anim)
        leftToCenterAnim = AnimationUtils.loadAnimation(context, R.anim.left_to_center_anim)
    }

    private fun setupRecycler() {
        val adapter = FormAdapter(formViewModel)
        binding.formRecycler.itemAnimator = null
        binding.formRecycler.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        binding.formRecycler.adapter = adapter
        binding.formRecycler.addItemDecoration(FormItemDecoration(requireContext()))
        observeOptions(adapter)
    }

    private fun observeOptions(adapter: FormAdapter) {
        formViewModel.getOptions().observe(requireActivity(), {
            adapter.submitList(it.toMutableList())
            if(formViewModel.getStartShowingAnimations().value!! && !it.any { it.selected })
                binding.formContainer.startAnimation(
                    if(showNextPanel) rightToCenterAnim
                    else leftToCenterAnim)
        })
    }

    private fun observeCurrentMode() {
        formViewModel.getCurrentMode().observe(requireActivity(), {
            showNextPanel = it > lastMode
            if(showNextPanel) nextBtnClicked()
            else backBtnClicked()
            lastMode = it
        })
    }

    private fun nextBtnClicked() {
        if(!formViewModel.getStartShowingAnimations().value!!) return
        MainScope().launch {
            delay(400)
            binding.formContainer.startAnimation(centerToLeftAnim)
            delay(100)
            formViewModel.updateValues()
        }
    }

    private fun backBtnClicked() {
        if(!formViewModel.getStartShowingAnimations().value!!) return
        MainScope().launch {
            binding.formContainer.startAnimation(centerToRightAnim)
            delay(100)
            formViewModel.updateValues()
        }
    }
}