package com.diegoprado.desafio_android_diego_prado.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class ConvertMD5 {

    fun md5(stringToHash: String): String {
        val md5 = "MD5"

        try {
            val digest = MessageDigest.getInstance(md5)
            digest.update(stringToHash.toByteArray())
            val messageDigest = digest.digest()

            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var hex = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (hex.length < 2) {
                    hex = "0$hex"
                }
                hexString.append(hex)
            }
            return hexString.toString()

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        return ""
    }

}