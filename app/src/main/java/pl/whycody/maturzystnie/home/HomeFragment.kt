package pl.whycody.maturzystnie.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.whycody.maturzystnie.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

}