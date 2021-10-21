package pl.whycody.maturzystnie.data

data class Subject(
    val id: Int,
    val name: String,
    val type: String)

data class FormQuestion(
    val id: String,
    val title: String,
    val formOptions: List<FormOption>
)

data class FormOption(
    val id: String,
    val title: String,
    val subtitle: String,
    var selected: Boolean = false,
    val formQuestion: FormQuestion?
)