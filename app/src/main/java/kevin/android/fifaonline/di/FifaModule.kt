package kevin.android.fifaonline.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import kevin.android.fifaonline.BuildConfig
import kevin.android.fifaonline.api.Api
import kevin.android.fifaonline.api.MyInterceptor
import kevin.android.fifaonline.util.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by JihoKevin.
 * User: sinjiho
 * Date: 2021/09/16
 * Time: 12:26 오후
 */

@Module
@InstallIn(SingletonComponent::class)
object ApiProvider {

    @Singleton
    @Provides
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOkHttpClientWithoutAuthorization())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())).build()
    }

    @Provides
    fun getOkHttpClientWithoutAuthorization(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)

            val interceptor = HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }

            // 이 클라이언트를 통해 오고 가는 네트워크 요청/응답을 로그로 표시하도록 합니다.
            addInterceptor(interceptor)
            addInterceptor(MyInterceptor())
        }.build()
    }

    @Singleton
    @Provides
    fun provideFifaApi(retrofit: Retrofit) : Api = retrofit.create(Api::class.java)


}