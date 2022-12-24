package xyz.fi5t.robustapp.data.model

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import xyz.fi5t.robustapp.internal.ByteBufferAdapter
import java.nio.ByteBuffer

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String,

    @SerializedName("firstName")
    val firstName: String,

    @SerializedName("lastName")
    val lastName: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("token")
    @JsonAdapter(ByteBufferAdapter::class)
    var token: ByteBuffer
)
