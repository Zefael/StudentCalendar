package com.blueamber.studentcalendar.modules

import com.blueamber.studentcalendar.BuildConfig
import com.blueamber.studentcalendar.domain.remote.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideOkHttp(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BASIC
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return UnsafeOkHttpClient.getUnsafeOkHttpClient()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideJsonConverterFactory(): MoshiConverterFactory {
        val moshiBuilder = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
        return MoshiConverterFactory.create(moshiBuilder.build())
    }

    @Singleton
    @Provides
    fun provideNetworkJsonApi(jsonConverterFactory: MoshiConverterFactory,
                              client: OkHttpClient): NetworkJsonApi {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com")
            .addConverterFactory(jsonConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(NetworkJsonApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkJsonRepository(networkJsonJsonApi: NetworkJsonApi): NetworkJsonRepository {
        return NetworkJsonRepository(networkJsonJsonApi)
    }

    @Provides
    fun provideXmlConverterFactory(): SimpleXmlConverterFactory {
        return SimpleXmlConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideNetworkXmlApi(xmlConverterFactory: SimpleXmlConverterFactory,
                             client: OkHttpClient): NetworkXmlApi {
        return Retrofit.Builder()
            .baseUrl("https://edt-st.u-bordeaux.fr")
            .addConverterFactory(xmlConverterFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()
            .create(NetworkXmlApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkXmlRepository(networkXmlApi: NetworkXmlApi): NetworkXmlRepository {
        return NetworkXmlRepository(networkXmlApi)
    }
}