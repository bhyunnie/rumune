package com.rumune.web.domain.store.repository

import com.rumune.web.domain.store.Entity.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface StoreRepository:JpaRepository<Store, Long> {
    fun findByStoreId(storeId:Long):Optional<Store>
    fun findByName(storeName:String):Optional<Store>
}