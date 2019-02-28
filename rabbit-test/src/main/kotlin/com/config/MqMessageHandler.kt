package com.config

import org.springframework.messaging.Message

class MqMessageHandler{
    private val MSG = "%s received. Content: %s"

    fun process(msg: Message<String>) {
        val content = msg.payload

        System.out.println(String.format(MSG, content))
    }
}