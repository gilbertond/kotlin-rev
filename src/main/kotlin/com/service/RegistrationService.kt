package com.service

import com.entity.User
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Service
import org.springframework.web.context.WebApplicationContext

@Service
@Scope(WebApplicationContext.SCOPE_SESSION)
class RegistrationService {

    var user = User()

}