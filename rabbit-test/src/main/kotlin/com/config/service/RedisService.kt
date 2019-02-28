package com.config.service

import com.dao.RedisRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

//@Service
 class RedisService{
//    @Value("\$batch.size:1000")
    var batchSize:Int ?= 1000

     @Autowired
    lateinit var redisRepo: RedisRepo

    @Cacheable("books")
    open fun loadByBatch(batchSize: Int){
//       return redisRepo.findByBatch(batchSize)
    }
}