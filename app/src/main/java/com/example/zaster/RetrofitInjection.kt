package com.example.zaster

import com.example.zaster.repositories.PostLoginInfo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface NetworkComponent {
    fun inject(mainActivity: MainActivity)
}

@Module
class RetrofitModule {

    private val BASE_URL = "https://www.google.com"

    @Singleton
    @Provides
    fun postLoginInfo(retrofit: Retrofit): PostLoginInfo {
        return PostLoginInfo(retrofit)
    }

    @Singleton
    @Provides
    fun retrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
    }
    @Provides
    fun gson() : Gson
    {
        return GsonBuilder().create()
    }
    @Provides
    fun okHttpClient() : OkHttpClient {

        return OkHttpClient.Builder()
                .build()
    }
}