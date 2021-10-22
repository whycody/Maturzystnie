package pl.whycody.maturzystnie.home.form

import android.content.Context
import pl.whycody.maturzystnie.R

class FormQuestionsProvider(private val context: Context) {

    fun getQuestion(currentMode: Int): String {
        return when(currentMode) {
            FormViewModel.CHOOSE_SUBJECT_MODE -> context.getString(R.string.choose_subject)
            FormViewModel.CHOOSE_STUDY_MODE -> context.getString(R.string.choose_study_mode)
            FormViewModel.CHOOSE_YEAR_MODE -> context.getString(R.string.choose_year)
            FormViewModel.CHOOSE_CATEGORY_MODE -> context.getString(R.string.choose_category)
            else -> ""
        }
    }

    fun getOptionTitle(id: String): String {
        return when(id) {
            FormViewModel.TRAINING -> context.getString(R.string.training)
            else -> context.getString(R.string.sheet)
        }
    }
}