package com.config

import com.domain.Person
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.core.support.BatchingStrategy
import org.springframework.amqp.rabbit.core.support.SimpleBatchingStrategy
import org.springframework.amqp.support.converter.DefaultClassMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.channel.MessageChannels
//import org.springframework.integration.dsl.MessageChannels
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import java.util.*


@Configuration
open class RabbitConfig{

    @Autowired
    private val rabbitConnectionFactory: ConnectionFactory? = null

    @Autowired
    private val jsonMessageConverter: Jackson2JsonMessageConverter ?= null

    @Autowired
    private val batchingStrategy: BatchingStrategy ?= null

    @Bean
    open fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)

        template.messageConverter = jsonMessageConverter
        
        return template
    }

    @Bean
    open fun batchingRabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val batchingRabbitTemplate = BatchingRabbitTemplate(batchingStrategy, ConcurrentTaskScheduler())

        batchingRabbitTemplate.connectionFactory = connectionFactory
        batchingRabbitTemplate.messageConverter = jsonMessageConverter

        return batchingRabbitTemplate
    }

    @Bean(name = ["batchingStrategy"])
    open fun createBatchingStrategy(): BatchingStrategy
    {
        //TODO play with me to set up batch sizes!!!!!!
        return SimpleBatchingStrategy(1000, 1000, 1000)
        
    }

    @Bean
    open fun mapper(): ObjectMapper{
        return ObjectMapper()
    }

        @Bean
    open fun outBoundChannel(): DirectChannel {
        return MessageChannels.direct().get()
    }

    @Bean
    open fun gilQueue(): Queue {
        return Queue("gil.queue", true, false, false)
    }

    @Bean
    open fun otherQueue(): Queue {
        return Queue("gil.other.queue", true, false, false)
    }

    @Bean
    open fun jsonMessageConverter(): Jackson2JsonMessageConverter {
        val jsonConverter = Jackson2JsonMessageConverter()
        jsonConverter.classMapper = classMapper()
        return jsonConverter
    }

    @Bean
    open fun classMapper(): DefaultClassMapper {
        val classMapper = DefaultClassMapper()
        val idClassMapping = HashMap<String, Class<*>>()
        idClassMapping["Person"] = Person::class.java
//        idClassMapping["Person2"] = Person2::class.java       //This can be another class to map
        classMapper.setIdClassMapping(idClassMapping)
        classMapper.setTrustedPackages("com.domain")
        
        return classMapper
    }
}