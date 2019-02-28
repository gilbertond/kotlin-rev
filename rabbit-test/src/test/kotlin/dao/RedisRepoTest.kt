package dao

import com.dao.RedisRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.RedisServer



class RedisRepoTest{
    @Autowired
    lateinit var redisRepo: RedisRepo

    private val redisServer: RedisServer? = null
}