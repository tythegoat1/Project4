package com.example.project4

class LatestMovie {
    @SerializedName("poster_path")
    var movieImageUrl: String? = null

    annotation class SerializedName(val value: String)

    //    @JvmField
    @SerializedName("title")
    var title: String? = null

    @SerializedName("overview")
    var description: String? = null
}
