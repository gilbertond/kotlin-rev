package com.config

import org.springframework.messaging.Message

class FileProcessor{
    private val HEADER_FILE_NAME = "file_name"
    private val MSG = "%s received. Content: %s"

    fun process(msg: Message<String>) {

        val fileName = msg.headers[HEADER_FILE_NAME] as String
        val content = msg.payload

        System.out.println(String.format(MSG, fileName, content))
    }
}