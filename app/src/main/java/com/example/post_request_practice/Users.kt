package com.example.post_request_practice

import com.google.gson.annotations.SerializedName

class Users {

    @SerializedName("data")
    var data: List<UserDetails>? = null

    class UserDetails {
        @SerializedName("name")
        var name: String? = null

        @SerializedName("location")
        var location: String? = null

        @SerializedName("pk")
        var pk: Int? = null

        constructor(name: String?, location: String?, pk: Int?) {
            this.name = name
            this.location = location
            this.pk = pk
        }
    }
}