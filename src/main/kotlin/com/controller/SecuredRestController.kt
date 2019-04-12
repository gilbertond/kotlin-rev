package com.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.lang.annotation.Documented
import java.lang.reflect.Method

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Documented
@RestController
@RequestMapping("/secured/api/")
annotation class SecuredRestController

@SecuredRestController
public class Hello1Controller {

    @RequestMapping("securedEndPoint1")
    fun secured1(): String{

        return "I am secured 1"
    }

    @RequestMapping(value = ["securedEndPoint2"], method = [RequestMethod.GET, RequestMethod.GET])
    fun secured2(): String{

        return "I am secured 2"
    }

    @RequestMapping(value = ["securedEndPoint3"], method = [RequestMethod.POST, RequestMethod.PUT])
    fun secured3(): String{

        return "I am secured 3"
    }
}