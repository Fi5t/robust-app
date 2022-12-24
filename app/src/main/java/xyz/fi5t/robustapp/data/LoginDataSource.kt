package xyz.fi5t.robustapp.data

import xyz.fi5t.robustapp.data.model.LoggedInUser
import xyz.fi5t.robustapp.internal.DummyApi
import xyz.fi5t.robustapp.internal.LoginRequest
import java.io.IOException
import javax.inject.Inject

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource @Inject constructor(private val api: DummyApi) {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            val response = api.login(LoginRequest(username, password))
            Result.Success(response)
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
