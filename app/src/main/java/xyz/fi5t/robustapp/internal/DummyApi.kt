package xyz.fi5t.robustapp.internal

import retrofit2.http.Body
import retrofit2.http.POST
import xyz.fi5t.robustapp.data.model.LoggedInUser

interface DummyApi {
    @POST("/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoggedInUser
}
