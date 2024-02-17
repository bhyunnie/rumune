package com.rumune.web.domain.store.dto

data class RequestUpdateStoreDto(
    val storeId:Long,
    val name:String,
    val description:String
) {
}