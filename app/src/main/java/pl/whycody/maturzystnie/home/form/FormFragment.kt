package pl.whycody.maturzystnie.home.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.whycody.maturzystnie.databinding.FragmentFormBinding

class FormFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentFormBinding.inflate(inflater)
        return binding.root
    }
}