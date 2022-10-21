package org.cornfarmer.di

import android.app.Application
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Application : Application() {
    private val API_URL = "http://3.34.223.58:9000"

    companion object {
        lateinit var joinSharedPreferences: SharedPreferences
        lateinit var tokenSharedPreferences: SharedPreferences
        lateinit var sRetrofit: Retrofit

        val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"
    }

    override fun onCreate() {
        super.onCreate()

//        KakaoSdk.init(this, "e355d004b5a2901c09d0626d9f643ad9")

        joinSharedPreferences =
            applicationContext.getSharedPreferences("join", MODE_PRIVATE)
        tokenSharedPreferences =
            applicationContext.getSharedPreferences("token", MODE_PRIVATE)
        initRetrofitInstance()
    }

    private fun initRetrofitInstance() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(httpLoggingInterceptor) // API Response 로그 작성용
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()
        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
