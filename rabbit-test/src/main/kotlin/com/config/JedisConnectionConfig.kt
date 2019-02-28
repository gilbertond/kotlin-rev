package com.config

import com.domain.Person
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.connection.RedisServer
import org.springframework.data.redis.connection.RedisStandaloneConfiguration


@Configuration
//@EnableCaching
open class JedisConnectionConfig{

    private val redisServer: RedisServer? = null

    @Autowired
    private val env: Environment? = null
    
    @Bean
    open fun jedisConnectionFactory(): JedisConnectionFactory{

        //THIS OR THE .properties file
        var jedisConnectionFactory = RedisStandaloneConfiguration()
//        jedisConnectionFactory.hostName="localhost"
//        jedisConnectionFactory.password= RedisPassword.of("some pass")
        return JedisConnectionFactory()
    }

    @Bean
    open fun redisTemplate(): RedisTemplate<String, Person>{
       var redisTemplate: RedisTemplate<String, Person> = RedisTemplate()

        redisTemplate.connectionFactory = jedisConnectionFactory()
        return redisTemplate
    }
}