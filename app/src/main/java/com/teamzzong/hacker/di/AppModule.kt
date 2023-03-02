package com.teamzzong.hacker.di

import android.app.Application
import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kakao.sdk.user.UserApiClient
import com.teamzzong.hacker.FlipperInitializer
import com.teamzzong.hacker.data.local.HackerDataStoreImpl
import com.teamzzong.hacker.data.remote.interceptor.AuthInterceptor
import com.teamzzong.hacker.data.util.FileParser
import com.teamzzong.hacker.design.ResolutionMetricsImpl
import com.teamzzong.hacker.di.qualifier.Auth
import com.teamzzong.hacker.di.qualifier.Logger
import com.teamzzong.hacker.domain.HackerDataStore
import com.teamzzong.hacker.navigator.AppNavigatorImpl
import com.teamzzong.hacker.shared.AppNavigator
import com.teamzzong.hacker.ui.design.ResolutionMetrics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    @ApplicationContext
    fun provideApplication(application: Application) = application

    @Provides
    @Singleton
    fun provideResolutionMetrics(
        @ApplicationContext context: Application
    ): ResolutionMetrics = ResolutionMetricsImpl(context)

    @Provides
    @Singleton
    fun provideKakaoClient(): UserApiClient = UserApiClient.instance

    @Provides
    @Singleton
    fun provideFileParser(
        @ApplicationContext context: Context
    ): FileParser = FileParser(context)

    @Provides
    @Singleton
    fun provideAppNavigator(appNavigator: AppNavigatorImpl): AppNavigator = appNavigator

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    @Logger
    fun provideHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    @Auth
    fun provideAuthInterceptor(interceptor: AuthInterceptor): Interceptor = interceptor

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Logger loggingInterceptor: Interceptor,
        @Auth authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .apply { FlipperInitializer.addFlipperNetworkPlugin(this) }
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        factory: Converter.Factory
    ) = Retrofit.Builder()
        .baseUrl("http://3.36.251.74:5000/")
        .client(client)
        .addConverterFactory(factory)
        .build()

    @Provides
    @Singleton
    fun provideDataStore(store: HackerDataStoreImpl): HackerDataStore = store
}