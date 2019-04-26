package com.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
open class RabbitBrokerConnectionProperties {

    private var host: String? = null

    private var port:Int = 0

    private var username: String? = null

    private var password: String? = null

    private var dynamic = true

    fun getHost(): String? {
        return this.host
    }

    fun setHost(host: String) {
        this.host = host
    }

    fun getPort(): Int {
        return this.port
    }

    fun setPort(port: Int) {
        this.port = port
    }

    fun getUsername(): String? {
        return this.username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getPassword(): String? {
        return this.password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun isDynamic(): Boolean {
        return this.dynamic
    }

    fun setDynamic(dynamic: Boolean) {
        this.dynamic = dynamic
    }
}