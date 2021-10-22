package pl.whycody.maturzystnie.home.form

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.whycody.maturzystnie.data.ApiService
import pl.whycody.maturzystnie.data.FormOption

class FormViewModel(private val apiService: ApiService,
                    private val questionsProvider: FormQuestionsProvider): ViewModel(), FormInteractor {

    private val options = MutableLiveData<List<FormOption>>(emptyList())
    private val selectedSubjectId = MutableLiveData<String>()
    private val selectedStudyModeId = MutableLiveData<String>()
    private val selectedCategoryId = MutableLiveData<String>()
    private val selectedYearId = MutableLiveData<String>()
    private val backOption = FormOption("back", "Wróć")
    private var currentMode = MutableLiveData(CHOOSE_SUBJECT_MODE)
    val currentQuestion = MutableLiveData(questionsProvider.getQuestion(currentMode.value!!))

    fun getOptions() = options

    fun getCurrentMode() = currentMode

    init {
        viewModelScope.launch { postSubjectsOptions() }
    }

    private fun getSubjectTypeFullName(type: String) =
        if(type == "P") "podstawa"
        else "rozszerzenie"

    override fun formOptionClicked(id: String) {
        updateView(id)
        when(currentMode.value!!) {
            CHOOSE_SUBJECT_MODE -> subjectSelected(id)
            CHOOSE_STUDY_MODE -> studyModeSelected(id)
            CHOOSE_CATEGORY_MODE -> categorySelected(id)
            CHOOSE_YEAR_MODE -> yearSelected(id)
        }
    }

    private fun updateView(id: String) {
        val currentOptions = options.value
        currentOptions?.forEach { formOption -> formOption.selected = false }
        currentOptions?.find { it.id == id }?.selected = true
        options.postValue(currentOptions)
    }

    private fun subjectSelected(id: String) {
        selectedSubjectId.postValue(id)
        currentMode.postValue(CHOOSE_STUDY_MODE)
    }

    private fun studyModeSelected(id: String) {
        when(id) {
            BACK -> {
                selectedSubjectId.postValue(null)
                currentMode.postValue(CHOOSE_SUBJECT_MODE)
                return
            }
            TRAINING -> currentMode.postValue(CHOOSE_CATEGORY_MODE)
            SHEET -> currentMode.postValue(CHOOSE_YEAR_MODE)
        }
        selectedStudyModeId.postValue(id)
    }

    private fun categorySelected(id: String) {
        if(id == BACK) {
            selectedStudyModeId.postValue(null)
            currentMode.postValue(CHOOSE_STUDY_MODE)
            return
        }
        selectedCategoryId.postValue(id)
    }

    private fun yearSelected(id: String) {
        if(id == BACK) {
            selectedStudyModeId.postValue(null)
            currentMode.postValue(CHOOSE_STUDY_MODE)
            return
        }
        selectedYearId.postValue(id)
    }

    fun updateValues() {
        currentQuestion.postValue(questionsProvider.getQuestion(currentMode.value!!))
        when(currentMode.value!!) {
            CHOOSE_SUBJECT_MODE -> viewModelScope.launch { postSubjectsOptions() }
            CHOOSE_STUDY_MODE -> postStudyModeOptions()
            CHOOSE_CATEGORY_MODE -> viewModelScope.launch { postCategoriesOptions() }
            CHOOSE_YEAR_MODE -> viewModelScope.launch { postYearOptions() }
        }
    }

    private suspend fun postSubjectsOptions() {
        val response = apiService.getAllSubjects()
        if(response.isSuccessful && currentMode.value == CHOOSE_SUBJECT_MODE) {
            val optionsList = response.body()?.map {
                    subject -> FormOption(subject.id.toString(),
                subject.name, getSubjectTypeFullName(subject.type)) }
            options.postValue(optionsList)
        }
    }

    private fun postStudyModeOptions() {
        if(currentMode.value != CHOOSE_STUDY_MODE) return
        backOption.selected = false
        options.postValue(listOf(backOption, FormOption(TRAINING,
            questionsProvider.getOptionTitle(TRAINING)),
            FormOption(SHEET, questionsProvider.getOptionTitle(SHEET))))
    }

    private suspend fun postCategoriesOptions() {
        val response = apiService.getAllCategories(selectedSubjectId.value!!)
        if(response.isSuccessful && currentMode.value == CHOOSE_CATEGORY_MODE) {
            backOption.selected = false
            val optionsList = response.body()?.map {
                    category -> FormOption(category.id.toString(), category.name) }
            options.postValue(listOf(backOption).plus(optionsList as List<FormOption>))
        }
    }

    private suspend fun postYearOptions() {
        val response = apiService.getCkeYears(selectedSubjectId.value!!)
        if(response.isSuccessful && currentMode.value == CHOOSE_YEAR_MODE) {
            backOption.selected = false
            val optionsList = response.body()?.map { year -> FormOption(year.toString(), year.toString()) }
            options.postValue(listOf(backOption).plus(optionsList as List<FormOption>))
        }

    }

    companion object {
        const val CHOOSE_SUBJECT_MODE = 0
        const val CHOOSE_STUDY_MODE = 1
        const val CHOOSE_CATEGORY_MODE = 2
        const val CHOOSE_YEAR_MODE = 3
        const val GENERATE_SHEET_MODE = 4

        const val BACK = "back"
        const val TRAINING = "training"
        const val SHEET = "sheet"
    }
}