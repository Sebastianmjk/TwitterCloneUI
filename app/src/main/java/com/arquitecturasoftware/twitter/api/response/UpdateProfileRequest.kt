import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("name") val name: String,
    @SerializedName("password") val password: String
)