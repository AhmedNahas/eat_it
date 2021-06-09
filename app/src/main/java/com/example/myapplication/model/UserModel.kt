package com.example.myapplication.model

import java.io.Serializable

class UserModel : Serializable{
    var address: String? = null
    var name: String? = null
    var phone: String? = null
    var uid : String? = null

    constructor(address: String?, name: String?, phone: String?, uid: String?) {
        this.address = address
        this.name = name
        this.phone = phone
        this.uid = uid
    }
}