package pl.whycody.maturzystnie

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.dsl.module
import pl.whycody.maturzystnie.data.ApiService
import pl.whycody.maturzystnie.data.BASE_URL
import pl.whycody.maturzystnie.home.form.FormViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}

val appModule = module {

    viewModel { FormViewModel(get()) }

    single<Retrofit> {
        return@single Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(androidContext()))
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}

private fun getOkHttpClient(context: Context) =
    OkHttpClient.Builder()
        .cache(getCache(context))
        .addInterceptor { chain ->
            var request = chain.request()
            request =
                if (hasNetwork(context))
                    request.newBuilder().header("Cache-Control",
                        "public, max-age= 5").build()
                else request.newBuilder().header("Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
            chain.proceed(request)
        }.build()

private fun getCache(context: Context): Cache {
    val cacheSize = (5 * 1024 * 1024).toLong()
    return Cache(context.cacheDir, cacheSize)
}

private fun hasNetwork(context: Context): Boolean {
    var isConnected = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if(activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}