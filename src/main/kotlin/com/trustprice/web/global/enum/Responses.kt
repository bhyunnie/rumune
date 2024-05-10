package com.trustprice.web.global.enum

import org.springframework.http.HttpStatus

enum class Responses(status: HttpStatus, message: String, code: Int) {
    OK(HttpStatus.OK, message = "API REQUEST SUCCEED", 10201),
    NOT_FOUND(HttpStatus.NOT_FOUND, message = "DATA NOT FOUNDED", 10401),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR, message = "INTERNAL SERVER ERROR", 10501),
}
