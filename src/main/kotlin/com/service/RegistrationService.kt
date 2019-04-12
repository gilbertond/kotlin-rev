package com.service

import com.entity.Person
import com.entity.User
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext
import java.util.*

@Service
@Scope(WebApplicationContext.SCOPE_SESSION)
class RegistrationService {

    var user = User()

    fun getPerson(id: Long): Person{
        return Person(id, "test", "test", Date())
    }
}