package pl.whycody.maturzystnie.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.whycody.maturzystnie.MainNavigation
import pl.whycody.maturzystnie.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(inflater)
        GlobalScope.launch {
            delay(1000)
            (activity as MainNavigation).showHomeFragment()
        }
        return binding.root
    }
}