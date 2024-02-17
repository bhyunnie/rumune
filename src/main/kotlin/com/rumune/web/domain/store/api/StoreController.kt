package com.rumune.web.domain.store.api

import com.rumune.web.domain.store.application.StoreService
import com.rumune.web.domain.store.dto.RequestCreateStoreDto
import com.rumune.web.domain.store.dto.RequestUpdateStoreDto
import com.rumune.web.domain.store.dto.ResponseStoreListDto
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
                    data = storeList,
                    success = true,
                )
            )

    }
    @PostMapping("/api/v1/store/write")
    fun createStore(@RequestBody request: RequestCreateStoreDto):ResponseEntity<String> {
        val result = storeService.createStore(request.name,request.description)
        println(result)
        return ResponseEntity.ok("eee")
    }
    @Transactional
    @PutMapping("/api/v1/store/write")
    fun updateStore(@RequestBody request: RequestUpdateStoreDto):ResponseEntity<ResponseStoreListDto> {
        val result = storeService.updateStoreInfo(request.storeId, request.name, request.description)
        return if (result) {
            ResponseEntity.ok(
                ResponseStoreListDto(
                    message = "업데이트 완료",
                    success = true,
                    data = listOf(),
                )
            )
        } else {
            ResponseEntity.ok(
                ResponseStoreListDto(
                    message = "엔티티를 찾을 수 없습니다!",
                    success = false,
                    data = listOf(),
                )
            )
        }
    }
    @Transactional
    @DeleteMapping("/api/v1/store/{storeId}")
    fun deleteStore(@PathVariable storeId: Long):ResponseEntity<ResponseStoreListDto> {
        val result = storeService.deleteStore(storeId)
        return if (result) {
            ResponseEntity.ok(
                ResponseStoreListDto(
                    message = "삭제 완료",
                    success = true,
                    data = listOf(),
                )
            )
        } else {
            ResponseEntity.ok(
                ResponseStoreListDto(
                    message = "엔티티를 찾을 수 없습니다.",
                    success = false,
                    data = listOf(),
                )
            )
        }
    }
}