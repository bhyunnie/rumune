package com.trustprice.web.domain.common.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CommonApi {
    @GetMapping("/")
    fun index(): ResponseEntity<String> {
        return ResponseEntity.ok("this is rumune")
    }

    @GetMapping("/api")
    fun indexing(): ResponseEntity<String> {
        return ResponseEntity.ok("this is rumune api server")
    }
}
