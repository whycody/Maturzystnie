package pl.whycody.maturzystnie.data

import retrofit2.Response
import retrofit2.http.GET

const val BASE_URL = "https://poznajmikolajakopernika.pl/api/"

interface ApiService {

    @GET("get_subjects")
    suspend fun getAllSubjects(): Response<List<Subject>>
}