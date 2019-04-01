package com.config

import com.domain.Person
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.jms.support.converter.MessageConverter
import org.springframework.stereotype.Component
import javax.jms.Message
import javax.jms.Session
import javax.jms.TextMessage
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration

@Component
@Configuration
class CustomMessageConverter: MessageConverter {

    private val mapper = ObjectMapper()

    override fun toMessage(`object`: Any, session: Session): Message {

        //Object is the payload to be converted to json before sending to destination
        var payload: String?=null
        var person = `object` as Person

        try {
            payload = mapper.writeValueAsString(person)
            LOGGER.info("outbound json='{}'", payload)
        } catch (e: JsonProcessingException) {
            LOGGER.error("error converting form person", e)
        }

        val textMessage: TextMessage = session.createTextMessage()

        textMessage.text = payload

        return textMessage
    }

    override fun fromMessage(message: Message): Any {
        //Convert received message payload to Object
        val textMessage: TextMessage = message as TextMessage
        val payload = textMessage.text

        LOGGER.info("inbound json='{}'", payload)
        var person: Person?=null

        try {
            person = mapper.readValue(payload, Person::class.java)
        } catch (e: Exception) {
            LOGGER.error("error converting to person", e)
        }

        return person as Any
    }

    private val LOGGER = LoggerFactory.getLogger(CustomMessageConverter::class.java)
}