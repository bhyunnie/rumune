package com.rumune.web.domain.store.application

import com.rumune.web.domain.store.Entity.Store
import com.rumune.web.domain.store.repository.StoreRepository
import io.jsonwebtoken.io.IOException
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class StoreService(
    private val storeRepository: StoreRepository
) {
    // ETC
    // Create
    fun createStore(name:String, description:String):Store {
        return storeRepository.save(Store(name=name, description = description))
    }
    // Read
    fun findStoreList():List<Store> {
        return storeRepository.findAll()
    }

    fun findStoreByName(name:String):List<Store> {
        val storeOptional = storeRepository.findByName(name)
        return if(storeOptional.isPresent) {
            mutableListOf(storeOptional.get())
        } else {
            mutableListOf()
        }
    }
    // Update
    fun updateStoreInfo(storeId:Long, name:String, description: String): Boolean {
         val storeOptional = storeRepository.findByStoreId(storeId)
        if (storeOptional.isPresent) {
            val store = storeOptional.get()
            store.name = name
            store.description = description
            storeRepository.save(store)
            return true
        } else {
            return false
        }
    }
    // Delete
    fun deleteStore(storeId:Long): Boolean {
        return storeRepository.deleteStoreByStoreId(storeId) > 0
    }
}