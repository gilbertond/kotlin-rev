package com.security

import com.entity.User
import com.utils.*
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.stereotype.Component
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.*
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
public class GenerateEncryption {

    private val algorithm = "PBKDF2WithHmacSHA512"

    fun encrypyt(clientHashingSecret: String?, authSecret: String?): Any {

        val secretKeyFactory = SecretKeyFactory.getInstance(algorithm)

        val pbeKeySpec = PBEKeySpec(authSecret!!.toCharArray(), clientHashingSecret!!.toByteArray(), 100, 256)

        val secretKey = secretKeyFactory.generateSecret(pbeKeySpec)

        val byteArray = secretKey.encoded

        return Base64.encodeBase64String(byteArray)
    }

    fun generateToken(username: String?): String{
       val dateTime: ZonedDateTime = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.MILLIS)

        val  token: String =    Jwts.builder()
                                    .setSubject(username)
                                    .setExpiration(Date.from(dateTime.toInstant()))
                                    .signWith(SignatureAlgorithm.HS256, SECRET)
                                    .compact()

        return token
    }
}