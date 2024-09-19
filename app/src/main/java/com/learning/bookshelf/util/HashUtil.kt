package com.learning.bookshelf.util

import java.security.MessageDigest

object HashUtil {
    fun hashPwd(input:String) : String{
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(input.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it)}
    }
}