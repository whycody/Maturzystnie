package pl.whycody.maturzystnie.home.header

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.whycody.maturzystnie.databinding.FragmentHeaderBinding

class HeaderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentHeaderBinding.inflate(inflater)
        return binding.root
    }

}