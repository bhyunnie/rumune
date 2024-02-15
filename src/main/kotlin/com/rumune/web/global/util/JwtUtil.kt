package com.rumune.web.global.util

import com.rumune.web.domain.user.entity.User
import com.rumune.web.global.properties.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil(
    private val jwtProperties: JwtProperties
) {
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
    private val accessTokenExpirationDuration = jwtProperties.accessTokenExpireDuration
    private val refreshTokenExpirationDuration = jwtProperties.refreshTokenExpireDuration

    fun generateAccessToken(email: String, additionalClaims: Map<String,Any> = emptyMap()):String {
        return Jwts.builder()
            .header()
            .type("JWT")
            .and()
            .claims()
            .subject(email)
            .issuer(jwtProperties.issuer)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + accessTokenExpirationDuration))
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()
    }

    fun generateRefreshToken(email: String, additionalClaims: Map<String,Any> = emptyMap()):String {
        return Jwts.builder()
            .header()
            .and()
            .claims()
            .subject(email)
            .issuer(jwtProperties.issuer)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + refreshTokenExpirationDuration))
            .add(additionalClaims)
            .and()
            .signWith(secretKey)
            .compact()
    }

    fun validToken(token:String, user:User):Boolean {
        val email = getEmailOfToken(token)
        return user.email == email && !isExpired(token)
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