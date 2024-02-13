package com.rumune.web

import com.rumune.web.domain.user.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.reflect.full.memberProperties

class JwtTest {
    private val secretKey = Keys.hmacShaKeyFor("a".repeat(100).toByteArray())
    private val claims = mapOf("name" to "abh", "age" to 29, "tall" to 180)
    private fun printToken (token: String) {
        val tokenArray = token.split(".").map{ it.trim() }
        val header = String(Base64.getDecoder().decode(tokenArray[0]))
        val body = String(Base64.getDecoder().decode(tokenArray[1]))
        println("=== === === === === === === === === JWT TOKEN === === === === === === === === === === ")
        println(header)
        println(body)
        println("=== === === === === === === === === === === === === === === === === === === === === === ")
    }
    @DisplayName("1. jjwt 를 이용한 테스트")
    @Test
    fun test_1() {
        val token = Jwts
            .builder()
            .claims(claims)
            .signWith(secretKey)
            .compact()

        printToken(token)
        val tokenInfo:Jws<Claims> = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        println(tokenInfo)
    }

    @DisplayName("2. 만료 시간 테스트")
    @Test
    fun test_2() {
        try {
            val token = Jwts
                .builder()
                .claims(claims)
                .signWith(secretKey)
                .expiration(Date(System.currentTimeMillis() + 1000))
                .compact()

            Thread.sleep(2000)
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
            println("토큰이 만료되지 않았습니다")
        } catch (e:Exception) {
            println("토큰 만료 되었습니다.")
        }
    }

    @DisplayName("3. 테스트")
    @Test
    fun test_3() {
        val user = User(email = "1",name="2", provider ="3", pwd="4")
        val toMap:MutableMap<String,Any?> = mutableMapOf()
        User::class.memberProperties.forEach{ property ->
            toMap[property.name] = property.get(user)
        }

    }
}