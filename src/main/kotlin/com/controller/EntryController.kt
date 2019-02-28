package com.ccontroller

import com.entity.ErrorMessage
import com.entity.Person
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class EntryController{

    @RequestMapping(value = "/unauthorized", method = [(RequestMethod.GET), (RequestMethod.POST), (RequestMethod.PUT), (RequestMethod.DELETE), (RequestMethod.PATCH)])
    fun unauthorizedAccessMapping(): ResponseEntity<Any> {
        return ResponseEntity(ErrorMessage(401, "Unauthorised access"), HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("all")
    fun getAll() =
            listOf (
                        Person(22, "bronia", "kisakye", Date()),
                        Person(23, "gilbert", "ndenzi", Date())
                    )

    @GetMapping("person/{sid}")
    fun getPerson(@RequestParam("sid") sid: String) =
            listOf (
                    Person(22, "bronia", "kisakye", Date()),
                    Person(23, "gilbert", "ndenzi", Date())
            )//[Int.v]
}