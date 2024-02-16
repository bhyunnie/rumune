package com.rumune.web.domain.store.api

import com.rumune.web.domain.store.Entity.Store
import com.rumune.web.domain.store.application.StoreService
import com.rumune.web.domain.store.dto.RequestCreateStoreDto
import com.rumune.web.domain.store.dto.ResponseStoreListDto
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController(
    private val storeService: StoreService
) {
    @GetMapping("/api/v1/store/list")
    fun findStoreList():ResponseEntity<ResponseStoreListDto> {
        val storeList = storeService.findStoreList()
        return ResponseEntity.ok(
                ResponseStoreListDto(
                    message = "message 입니다",
                    data = storeList
                )
            )

    }

    @PostMapping("/api/v1/store/write")
    fun createStore(requestCreateStoreDto: RequestCreateStoreDto):ResponseEntity<String> {
        val result = storeService.createStore(requestCreateStoreDto.name,requestCreateStoreDto.description)
        println(result)
        return ResponseEntity.ok("eee")
    }
}