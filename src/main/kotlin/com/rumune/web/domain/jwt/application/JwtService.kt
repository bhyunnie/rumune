package com.rumune.web.domain.jwt.application

import com.rumune.web.domain.user.entity.User
import com.rumune.web.global.properties.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService(
    private val jwtProperties: JwtProperties
) {
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
   fun generateToken(email: String, expiredAt: Date, additionalClaims: Map<String,Any> = emptyMap()):String {
       return Jwts.builder()
           .claims()
           .subject(email)
           .issuer(jwtProperties.issuer)
           .issuedAt(Date(System.currentTimeMillis()))
           .expiration(expiredAt)
           .add(additionalClaims)
           .and()
           .signWith(secretKey)
           .compact()
   }
    fun validToken(token:String, userDetails:User):Boolean {
        val email = getEmailOfToken(token)
        return userDetails.email == email && !isExpired(token)
    }
    fun isExpired(token: String): Boolean {
        return getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))
    }
    private fun getAllClaims(token:String): Claims {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
    }
    fun getEmailOfToken(token: String):String? {
        return getAllClaims(token).subject
    }
}