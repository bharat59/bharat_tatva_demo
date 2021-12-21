package com.example.bharat_tatva_demo.di

import com.example.bharat_tatva_demo.network.ApiService
import com.example.bharat_tatva_demo.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val log = HttpLoggingInterceptor()
        log.level = HttpLoggingInterceptor.Level.BODY
        return log
    }

    @Provides
    @Singleton
    @Named("headers")
    fun provideHeader(): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request: Request.Builder = chain.request().newBuilder()
            chain.proceed(request.build())
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("headers") headerInterceptor: Interceptor?
    ): OkHttpClient {
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        headerInterceptor?.let { interceptor ->
            client.readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor) // for debug purposes
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }
}