package pl.whycody.maturzystnie.home.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.whycody.maturzystnie.data.ApiService
import pl.whycody.maturzystnie.data.Subject

class FormViewModel(private val apiService: ApiService): ViewModel() {

    private val subjects = MutableLiveData<List<Subject>>(emptyList())

    fun getSubjects() = subjects

    init {
        viewModelScope.launch {
            val response = apiService.getAllSubjects()
            if(response.isSuccessful) subjects.postValue(response.body())
        }
    }
}