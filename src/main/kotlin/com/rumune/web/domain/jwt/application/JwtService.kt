package com.rumune.web.domain.jwt.application

import com.rumune.web.domain.jwt.entity.JsonWebToken
import com.rumune.web.domain.jwt.repository.JwtRepository
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.domain.user.entity.User
import com.rumune.web.domain.user.repository.UserRepository
import com.rumune.web.global.properties.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtService(
    private val jwtProperties: JwtProperties,
    private val jwtRepository: JwtRepository,
    private val userRepository: UserRepository
) {
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())
    private val accessTokenExpirationDuration = jwtProperties.accessTokenExpireDuration
    private val refreshTokenExpirationDuration = jwtProperties.refreshTokenExpireDuration

    fun refreshAccessToken(token:String):Map<String,String> {
        val email = getEmailOfToken(token)
        val user = userRepository.findByEmail(email)
        if (user.isEmpty()) throw Exception("유저 정보가 없습니다.")
        val isVerified =  validRefreshToken(token,user[0], email)
        if(isVerified) {
            val oldToken = jwtRepository.findByJwt(token)[0]
            val accessToken = generateAccessToken(email)
            val refreshToken = generateRefreshToken(email)
            oldToken.jwt = refreshToken
            jwtRepository.save(oldToken)
            return mapOf("accessToken" to accessToken, "refreshToken" to refreshToken)
        }
        return mapOf()
    }

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

    fun validToken(token:String, user:User, email:String):Boolean {
        return user.email == email && !isExpired(token)
    }

    fun validRefreshToken(token:String, user:User, email:String):Boolean {
        return jwtRepository.findByJwt(token).isNotEmpty() && user.email == email && !isExpired(token)
    }

    fun isExpired(token: String): Boolean {
        return getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))
    }
    private fun getAllClaims(token:String): Claims {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
    }
    fun getEmailOfToken(token: String):String {
        val email = getAllClaims(token).subject
        if (email != null) {
            return email
        } else {
            throw Exception("이메일 정보가 없습니다.")
        }
    }
    fun save(jsonWebToken: JsonWebToken): JsonWebToken {
        return jwtRepository.save(jsonWebToken)
    }
    fun findJwt(userId:Long): List<JsonWebToken> {
        return jwtRepository.findByUserId(userId)
    }
}