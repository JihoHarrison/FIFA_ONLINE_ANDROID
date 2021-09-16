package kevin.android.fifaonline.api

import kevin.android.fifaonline.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                BuildConfig.FIFA_ACCESS_HEADER
            )
            .build()
        return chain.proceed(request)
    }
}