package com.rumune.web.domain.jwt.application

import com.amazonaws.services.kms.model.NotFoundException
import com.rumune.web.domain.jwt.entity.JsonWebToken
import com.rumune.web.domain.jwt.repository.JwtRepository
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

    /**
     * Access Token, Refresh Token 갱신
     */
    fun refreshTokens(token:String):Map<String,String> {
        val email = getEmailOfToken(token)
        val userOptional = userRepository.findByEmail(email)
        if(userOptional.isEmpty) throw NotFoundException("유저 정보가 없습니다.")
        val isVerified =  validRefreshToken(token, userOptional.get(), email)
        if(isVerified) {
            val oldToken = jwtRepository.findByJwt(token).get()
            val accessToken = generateAccessToken(email)
            val refreshToken = generateRefreshToken(email)
            oldToken.jwt = refreshToken
            jwtRepository.save(oldToken)
            return mapOf("accessToken" to accessToken, "refreshToken" to refreshToken)
        }
        return mapOf()
    }
    /**
     * access token 생성
     */
    fun generateAccessToken(email: String, additionalClaims: Map<String,Any> = emptyMap()):String {
        return Jwts.builder().header().type("JWT").and().claims()
            .subject(email).issuer(jwtProperties.issuer).issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + accessTokenExpirationDuration))
            .add(additionalClaims).and().signWith(secretKey).compact()
    }
    /**
     * refresh token 생성
     */
    fun generateRefreshToken(email: String, additionalClaims: Map<String,Any> = emptyMap()):String {
        return Jwts.builder().header().and().claims().subject(email)
            .issuer(jwtProperties.issuer).issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + refreshTokenExpirationDuration))
            .add(additionalClaims).and().signWith(secretKey).compact()
    }
    /**
     * token 검증
     */
    fun validToken(token:String, user:User, email:String):Boolean {
        return user.email == email && !isExpired(token)
    }
    /**
     * refresh token 검증
     */
    fun validRefreshToken(token:String, user:User, email:String):Boolean {
        return !(jwtRepository.findByJwt(token).isEmpty) && user.email == email && !isExpired(token)
    }

    /**
     * token 만료 검증
     */
    fun isExpired(token: String): Boolean {
        return getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))
    }

    /**
     * token 내의 claims 조회
     */
    private fun getAllClaims(token:String): Claims {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
    }

    /**
     * token 내의 email 조회
     */
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
    /**
     * Refresh Token 조회
     */
    fun findRefreshToken(refreshToken:String):JsonWebToken {
        val refreshTokenOptional = jwtRepository.findByJwt(refreshToken)
        if (refreshTokenOptional.isEmpty) throw NotFoundException("토큰을 찾을 수 없습니다.")
        return refreshTokenOptional.get()
    }
    /**
     * 토큰 갱신
     */
    fun updateJwt(userId:Long, jsonWebToken: String): JsonWebToken {
        try {
            val jwtOptional = jwtRepository.findByUserId(userId)
            val result: JsonWebToken
            if (jwtOptional.isEmpty) {
                result = jwtRepository.save(JsonWebToken(userId=userId,jwt=jsonWebToken))
            } else {
                val jwt = jwtOptional.get()
                jwt.jwt = jsonWebToken
                result = jwtRepository.save(jwt)
            }
            return result
        } catch(e: Exception) {
            throw Exception("토큰 갱신 중 에러가 발생했습니다")
        }
    }
}