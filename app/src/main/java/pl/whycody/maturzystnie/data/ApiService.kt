package pl.whycody.maturzystnie.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://maturzystnie.pl/api/"

interface ApiService {

    @GET("get_subjects")
    suspend fun getAllSubjects(): Response<List<Subject>>

    @GET("get_categories")
    suspend fun getAllCategories(@Query("subject_id") subjectId: String): Response<List<Category>>

    @GET("get_cke_sheets")
    suspend fun getCkeYears(@Query("subject_id") subjectId: String): Response<List<Int>>
}