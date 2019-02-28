package com.controller

import com.entity.ErrorMessage
import com.service.RegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.WebApplicationContext
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/congrats")
@Scope(WebApplicationContext.SCOPE_REQUEST)
class CongratsController(@Autowired val registrationService: RegistrationService) {

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun doGet(model: Model): String {
        print("Hi request")
        model.addAttribute("welcomeMessage", "Congratulations ${registrationService.user.firstName} ${registrationService.user.lastName}")
        model.addAttribute("user", registrationService.user)
        return "/congrats"
    }
    
}