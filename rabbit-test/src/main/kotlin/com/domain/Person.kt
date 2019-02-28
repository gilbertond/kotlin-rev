package com.domain

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@JsonDeserialize(`as` = Person::class)
@RedisHash("Person")
data class Person constructor(val id: Int?=0, val firstName: String?="", val lastName: String?=""): Serializable