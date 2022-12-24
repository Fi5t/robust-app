package xyz.fi5t.robustapp.internal

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)
