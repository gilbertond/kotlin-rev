package com.dao

import com.domain.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
open interface RedisRepo: CrudRepository<Person, String> {

//    @Query("SELECT * FROM Person limit $batchSize", nativ)
//    open fun findByBatch(batchSize: Int)
}