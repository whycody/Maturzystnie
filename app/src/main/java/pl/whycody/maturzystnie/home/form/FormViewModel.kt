package pl.whycody.maturzystnie.home.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.whycody.maturzystnie.data.ApiService
import pl.whycody.maturzystnie.data.FormOption

class FormViewModel(private val apiService: ApiService): ViewModel() {

    private val options = MutableLiveData<List<FormOption>>(emptyList())

    fun getOptions() = options

    init {
        viewModelScope.launch {
            val response = apiService.getAllSubjects()
            if(response.isSuccessful)
                options.postValue(response.body()?.map {
                        subject -> FormOption(subject.id.toString(), subject.name, false, null)
                })
        }
    }
}