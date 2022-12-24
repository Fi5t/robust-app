package xyz.fi5t.robustapp.internal

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.fi5t.robustapp.data.LoginDataSource
import xyz.fi5t.robustapp.data.LoginRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(rertofit: Retrofit): DummyApi = rertofit.create(DummyApi::class.java)

    @Provides
    fun provideLoginDataSource(api: DummyApi): LoginDataSource = LoginDataSource(api)

    @Provides
    @Singleton
    fun provideEncryptedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        val masterKey = MasterKeys.AES256_GCM_SPEC
        return EncryptedSharedPreferences.create(
            "account",
            masterKey.keystoreAlias,
            appContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    fun provideLoginRepository(
        dataSource: LoginDataSource,
        preferences: SharedPreferences
    ): LoginRepository = LoginRepository(dataSource, preferences)
}
