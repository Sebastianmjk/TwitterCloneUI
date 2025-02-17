package com.arquitecturasoftware.twitter.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("success") val success:Boolean)