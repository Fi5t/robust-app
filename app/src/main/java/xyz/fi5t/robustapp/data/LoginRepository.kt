package xyz.fi5t.robustapp.data

import android.content.SharedPreferences
import androidx.core.content.edit
import xyz.fi5t.robustapp.data.model.LoggedInUser
import xyz.fi5t.robustapp.internal.wipeDirectBuffer
import java.nio.ByteBuffer
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository @Inject constructor(
    private val dataSource: LoginDataSource,
    private val preferences: SharedPreferences
) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        user?.let { u ->
            preferences.edit {
                putString("accessToken", String(u.token.array()))
            }.also {
                u.token.wipeDirectBuffer()
                System.gc()
            }
        }
    }
}
