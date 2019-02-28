package com.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.ibm.mq.jms.MQQueue
import com.ibm.mq.jms.MQQueueConnectionFactory
import com.ibm.mq.spring.boot.MQConfigurationProperties
import com.ibm.msg.client.wmq.WMQConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
//import org.springframework.integration.dsl.MessageChannels
import org.springframework.integration.dsl.Pollers
import org.springframework.integration.dsl.channel.MessageChannels
//import org.springframework.integration.dsl.channel.MessageChannels
import org.springframework.integration.jms.dsl.Jms
import org.springframework.jca.cci.connection.ConnectionSpecConnectionFactoryAdapter
import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.converter.MessageConverter
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import javax.jms.ConnectionFactory
import javax.jms.Destination
import javax.jms.JMSException
import javax.jms.Queue

@Configuration
open class MQConfig {

    private val logger: Logger = Logger.getLogger(MQConfig::class.simpleName)

    @Autowired
    private lateinit var connectionProperties: ConnectionProperties

    @Autowired
    lateinit var jmsTemplate: JmsTemplate

    @Autowired
    lateinit var mqConfig: MQConfig

    @Autowired
    private val messageConverter: CustomMessageConverter ?= null

    @Bean
    open fun mqOutBoundFlow(): IntegrationFlow {
        logger.info("*********************\n Creating MQ outBoundChannel to send messages\n *****************")
        return IntegrationFlows.from(mqConfig.mqOutBoundChannel())     // or "outBoundChannel" call by name)
                .handle(
                        Jms.outboundAdapter(jmsTemplate).destination("gil.queue")//destination ca be bean of type Destination: This is a queue
                ).get()
    }

    @Bean
    open fun mqInBoundFlow(): IntegrationFlow {
        logger.info("*********************\n Creating MQ InBoundChannel to Read messages\n *****************")
        return IntegrationFlows.from(
                Jms.inboundAdapter(connectionFactory())
                        .configureJmsTemplate { t -> t.receiveTimeout(1000) }
                        .destination("some.queue")
        ) { e ->
            e.poller(
                    Pollers.fixedDelay(5000).maxMessagesPerPoll(2)
            )
        }.handle("mqMessageHandler", "process")
                .get()
    }

    //Router: Read and send messages to other channel
    @Bean
    open fun mqInAndOutBoundFlow(): IntegrationFlow {
        logger.info("*********************\n Creating In/Out Bound Channel to Read messages\n *****************")
        return IntegrationFlows.from(
                Jms.inboundAdapter(connectionFactory())
                        .configureJmsTemplate { t -> t.receiveTimeout(1000) }
                        .destination("some.queue")
        ) { e ->
            e.poller(
                    Pollers.fixedDelay(5000).maxMessagesPerPoll(2)
            )
        }.handle(Jms.outboundAdapter(connectionFactory())
         .destination("mqDestn"))
         .get()
    }

    @Bean
    open fun destinationQueue(): Destination {
        return MQQueue("gil.queue")
    }

    @Bean
    open fun mqOutBoundChannel(): DirectChannel {
        return MessageChannels.direct().get()
    }

    @Bean
    @Throws(NumberFormatException::class, JMSException::class)
    open fun jmsTemplate(): JmsTemplate {
        val template = JmsTemplate()
        template.messageConverter = messageConverter
        template.connectionFactory = connectionFactory()
        template.receiveTimeout = 1000
        return template
    }

    @Bean
    @Throws(NumberFormatException::class, JMSException::class)
    open fun connectionFactory(): ConnectionFactory {
        val mqQueueConnectionFactory = MQQueueConnectionFactory()
        mqQueueConnectionFactory.hostName = connectionProperties.hostName
//        mqQueueConnectionFactory.port = connectionProperties.port
        mqQueueConnectionFactory.channel = connectionProperties.channel
        mqQueueConnectionFactory.queueManager = connectionProperties.queueManager
        mqQueueConnectionFactory.transportType = WMQConstants.WMQ_CM_CLIENT

        val userCredentialsConnectionFactoryAdapter = UserCredentialsConnectionFactoryAdapter()
        userCredentialsConnectionFactoryAdapter.setUsername(connectionProperties.user)
        userCredentialsConnectionFactoryAdapter.setPassword(connectionProperties.password)
        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory)

        return userCredentialsConnectionFactoryAdapter
    }

    @Bean
    @Throws(JMSException::class)
    open fun targetQueue(): Queue {
        return MQQueue("some.queue")
    }

//    @Bean
//    open fun objectMapperBuilder(): Jackson2ObjectMapperBuilder {
//        var builder: Jackson2ObjectMapperBuilder = Jackson2ObjectMapperBuilder()
//        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
//        return builder
//    }

//    @JmsListener(destination = "mqDestn")
//    open fun listen(data: String) {
//        logger.info(data)
//    }
}