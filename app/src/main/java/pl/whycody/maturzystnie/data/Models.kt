package pl.whycody.maturzystnie.data

data class Subject(
    val id: Int,
    val name: String,
    val type: String)

data class Category(
    val id: Int,
    val name: String)

data class FormOption(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    var selected: Boolean = false)