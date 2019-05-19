package ru.prolitiichai.cleancity

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.prolitiichai.cleancity.api.CleanCityApi
import okhttp3.OkHttpClient
import ru.prolitiichai.cleancity.api.MyCookieJar


class App : Application() {

    companion object {
        var cleanCityApi: CleanCityApi? = null
        fun getApi(): CleanCityApi {
            return cleanCityApi!!
        }
    }

    var retrofit: Retrofit? = null

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
            .baseUrl("http://46.146.211.12:25567")
            .client(OkHttpClient().newBuilder()
                .cookieJar(MyCookieJar()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        cleanCityApi = retrofit!!.create(CleanCityApi::class.java)
    }
}