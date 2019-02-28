package com.security

import com.utils.HEADER_STRING
import com.utils.TOKEN_PREFIX
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Qualifier("customAuthenticationInterceptor")
class CustomSecurityAuthenticationInterceptor: HandlerInterceptorAdapter() {

    @Autowired
    private val generateEncryption: GenerateEncryption? = null

    // This is the shared public key between API and clients
    @Value("\${client.hashing.key}")
    private val clientHashingSecret: String? = null

    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {

        val authID = request!!.getHeader("authID")
        val authSecret = request.getHeader("authKey")
        var authorised: Boolean

        authorised = if (authID == null || authSecret == null || authID.isEmpty() || authSecret.isEmpty() || authID == "" || authSecret == ""){
            false
        }else{
            val hash = generateEncryption!!.encrypyt(clientHashingSecret, authSecret)
            hash != authID
        }

        return if (authorised){
            true
        }else{
            request.getRequestDispatcher("/unauthorised").forward(request, response)
            false
        }
    }

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {

        val token = generateEncryption!!.generateToken(request!!.getHeader("username"))
        response!!.writer.write(token)
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token)

    }
}