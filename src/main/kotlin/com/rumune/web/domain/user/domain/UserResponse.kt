package com.rumune.web.domain.user.domain

import org.springframework.http.HttpStatus

// code 10000 ~
enum class Responses(val status:HttpStatus, val message:String, val code: Int) {
    OK(HttpStatus.OK, message="API REQUEST SUCCEED", 10201),
    NOT_FOUND(HttpStatus.NOT_FOUND, message="DATA NOT FOUNDED", 10401),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR, message="INTERNAL SERVER ERROR", 10501)
}