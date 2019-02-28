package com.controller

import com.entity.User
import com.service.RegistrationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.ui.Model
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.WebApplicationContext
import javax.validation.Valid

@RestController
@RequestMapping("/")
@Scope(WebApplicationContext.SCOPE_REQUEST)
class ValidatorController(@Autowired val registrationService: RegistrationService) {

    @GetMapping(value = "")
    fun doGet(model : Model) : String {
        model.addAttribute("user", User())
        return "index"
    }

    @PostMapping("")
    fun doPost(@Valid user: User,  //The @Valid annotation tells Spring to Validate this object
               errors : Errors)  //Spring injects this class into this method. It will hold any
                                //errors that are found on the object
            : String {
        val result : String
        when {
            //Test for errors
            errors.hasErrors() -> result = "index"
            else -> {
                //Otherwise proceed to the next page
                registrationService.user = user
                result = "redirect:/congrats"
            }
        }
        return result
    }
}