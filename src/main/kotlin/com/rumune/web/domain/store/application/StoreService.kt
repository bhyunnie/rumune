package com.rumune.web.domain.store.application

import com.rumune.web.domain.store.Entity.Store
import com.rumune.web.domain.store.repository.StoreRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class StoreService(
    private val storeRepository: StoreRepository
) {
    fun findStoreList():MutableList<Store> {
        return storeRepository.findAll()
    }

    fun createStore(name:String, description:String):Store {
        return storeRepository.save(Store(name=name, description = description))
    }

}