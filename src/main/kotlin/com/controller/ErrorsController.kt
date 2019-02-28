package com.controller

import com.entity.ErrorMessage
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ErrorsControllers: ErrorController{
    override fun getErrorPath(): String {
        return "Bad request, No end point for your request!!"
    }
//    override fun getErrorPath(): String {
//        return "Bad request, No end point for your request!!"
//    }

    @GetMapping("/error", produces = ["application/json"])
    fun error() = ErrorMessage(404, "Wrong endpoint requested!}")
}