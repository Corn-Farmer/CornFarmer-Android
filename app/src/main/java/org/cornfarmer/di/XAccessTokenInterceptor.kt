package org.cornfarmer.di

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.cornfarmer.di.Application.Companion.X_ACCESS_TOKEN
import org.cornfarmer.di.Application.Companion.joinSharedPreferences
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = joinSharedPreferences.getString(X_ACCESS_TOKEN, null)
        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}
