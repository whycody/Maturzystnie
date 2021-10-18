package pl.whycody.maturzystnie

import androidx.fragment.app.Fragment

interface MainNavigation {

    fun navigateTo(fragment: Fragment, addToBackstack: Boolean = true)
}