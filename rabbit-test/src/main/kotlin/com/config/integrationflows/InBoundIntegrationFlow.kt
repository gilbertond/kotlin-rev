package com.config.integrationflows

//import org.springframework.integration.dsl.core.Pollers
import com.dao.RedisRepo
import com.domain.Person
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.amqp.dsl.Amqp
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.integration.core.MessageSource
//import org.springframework.integration.annotation.Poller
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice
import org.springframework.integration.store.MessageStore
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.MessageHandler
import org.springframework.retry.backoff.FixedBackOffPolicy
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import java.io.File
import java.nio.channels.FileChannel
import java.util.logging.Logger

@Configuration
open class InBoundIntegrationFlow{
     private val logger: Logger = Logger.getLogger(InBoundIntegrationFlow::class.simpleName)

//    @Autowired
//    private val rabbitConfig: RabbitConfig ?= null
//
//    @Autowired
//    private val connectionFactory: ConnectionFactory ?= null

//    @Bean
//    open fun simpleMessageListenerContainer(): SimpleMessageListenerContainer{
//
//        logger.info("...Loading listener configs for the Rabbit Connection...")
//         val listenerContainer = SimpleMessageListenerContainer()
//        listenerContainer.connectionFactory = connectionFactory
//        listenerContainer.addQueues(this.rabbitConfig!!.gilQueue(), this.rabbitConfig.otherQueue())  //
//        listenerContainer.setConcurrentConsumers(1)
//        listenerContainer.setExclusive(true)
//
//        return listenerContainer
//    }
    
//    @Bean
//    open fun inBoundFlow(): IntegrationFlow {
//        return IntegrationFlows.from(Amqp.inboundAdapter(simpleMessageListenerContainer()))
//                .transform(Transformers.objectToString())
////                .filter("json")
//                .handle(MessageHandler{
//                    Thread.sleep(3000)
//                     System.out.println(it.payload)
//                })
//                .get()
//    }

    @Autowired
    lateinit var mapper: ObjectMapper

    @Autowired
    lateinit var redisRepo: RedisRepo

//    @Autowired
//    private val jsonMessageConverter: Jackson2JsonMessageConverter ?= null

    @Autowired
    val outBoundChannel: MessageChannel?= null


    //TODO play around with queue size for release strategy
    @Bean
    open fun inComingPersonFlow(connectionFactory: ConnectionFactory): IntegrationFlow{
        return IntegrationFlows.from   (Amqp.inboundAdapter(connectionFactory, "gil.queue")  //Can add multiple queues here
//                                            .messageConverter(jsonMessageConverter)
                                        ).aggregate {
                                                a -> a.correlationStrategy {
                                                b -> b.headers["person"]
                                            }.releaseStrategy {
                                                c -> c.size() == 10
                                            }.discardChannel(outBoundChannel)//.async(true)
                                        }.handle(MessageHandler {                     //handle("simpleMessageHandler","process") is an option
                                        logger.info("\n >>>>>>>\n Loading message \n <<<<<<< \n")

                                        if (it.payload !is MutableList<*>) throw Exception("Invalid message!!")

                                        val persons =   it.payload as List<Person>
                                        logger.info("Size:  ${mapper.writerWithDefaultPrettyPrinter().writeValueAsString(persons)}")


                                        redisRepo.saveAll(it.payload as List<Person>)
                                    })
                                    .get()
    }

    @Bean
    open fun retryAdvice(): RequestHandlerRetryAdvice {
        val retryAdvice = RequestHandlerRetryAdvice()
        retryAdvice.setRetryTemplate(retryTemplate())
        return retryAdvice
    }

    @Bean
    open fun retryTemplate(): RetryTemplate {
        val retryTemplate = RetryTemplate()
        val retryPolicy = SimpleRetryPolicy()
        retryPolicy.maxAttempts = 3
        retryTemplate.setRetryPolicy(retryPolicy)

        val backOffPolicy = FixedBackOffPolicy()
        backOffPolicy.backOffPeriod = 2000
        retryTemplate.setBackOffPolicy(backOffPolicy)

        return retryTemplate
    }
}