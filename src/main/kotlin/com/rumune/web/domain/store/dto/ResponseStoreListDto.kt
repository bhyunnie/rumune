package com.rumune.web.domain.store.dto

import com.rumune.web.domain.store.Entity.Store

data class ResponseStoreListDto(
    val message:String,
    val success:Boolean,
    val data: List<Store>
) {
}