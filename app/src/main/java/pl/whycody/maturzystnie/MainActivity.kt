package pl.whycody.maturzystnie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import pl.whycody.maturzystnie.home.HomeFragment
import pl.whycody.maturzystnie.splash.SplashFragment

class MainActivity : AppCompatActivity(), MainNavigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) showSplashFragment()
    }

    private fun showSplashFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.mainContainer, SplashFragment())
            .commit()
    }

    override fun showHomeFragment() {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim, R.anim.enter_anim, R.anim.exit_anim)
            .replace(R.id.mainContainer, HomeFragment())
            .commit()
    }

    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim, R.anim.enter_anim, R.anim.exit_anim)
            .add(R.id.container, fragment)
        if (addToBackstack) transaction.addToBackStack(null)
        transaction.commit()
    }
}