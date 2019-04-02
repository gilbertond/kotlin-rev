package com.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.ibm.mq.jms.MQQueue
import com.ibm.mq.jms.MQQueueConnectionFactory
import com.ibm.mq.spring.boot.MQConfigurationProperties
import com.ibm.msg.client.wmq.WMQConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
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
import org.springframework.messaging.MessageChannel
import java.util.concurrent.TimeUnit
import java.util.logging.Logger
import javax.jms.ConnectionFactory
import javax.jms.Destination
import javax.jms.JMSException
import javax.jms.Queue

@Configuration
class MQConfig {

    private val logger: Logger = Logger.getLogger(MQConfig::class.simpleName)

    @Autowired
    private lateinit var connectionProperties: ConnectionProperties

    @Autowired
    lateinit var jmsTemplate: JmsTemplate

    @Autowired
    lateinit var mqConfig: MQConfig

//    @Autowired
//    @Qualifier("customMessageConverter")
    private val messageConverter: MessageConverter = CustomMessageConverter()

    @Bean
    fun mqOutBoundFlow(): IntegrationFlow {
        logger.info("*********************\n Creating MQ outBoundChannel to send messages\n *****************")
        return IntegrationFlows.from(mqConfig.mqOutBoundChannel())     // or "outBoundChannel" call by name)
                .handle(
                        Jms.outboundAdapter(jmsTemplate).destination("DEV.QUEUE.3")//destination ca be bean of type Destination: This is a queue
                ).get()
    }

    @Bean
    fun mqInBoundFlow(): IntegrationFlow {
        logger.info("*********************\n Creating MQ InBoundChannel to Read messages\n *****************")
        return IntegrationFlows.from(
                Jms.inboundAdapter(connectionFactory())
                        .configureJmsTemplate { t -> t.receiveTimeout(1000) }
                        .destination("DEV.QUEUE.1")
        ) { e ->
            e.poller(
                    Pollers.fixedDelay(5000).maxMessagesPerPoll(2)
            )
        }.handle("mqMessageHandler", "process")
                .get()
    }

    //Router: Read and send messages to other channel
    @Bean
    fun mqInAndOutBoundFlow(): IntegrationFlow {
        logger.info("*********************\n Creating In/Out Bound Channel to Read messages\n *****************")
        return IntegrationFlows.from(
                Jms.inboundAdapter(connectionFactory())
                        .configureJmsTemplate { t -> t.receiveTimeout(1000) }
                        .destination("DEV.QUEUE.1")
        ) { e ->
            e.poller(
                    Pollers.fixedDelay(5000).maxMessagesPerPoll(2)
            )
        }.handle(Jms.outboundAdapter(connectionFactory())
         .destination("mqDestn"))
         .get()
    }

    @Bean
    fun destinationQueue(): Destination {
        return MQQueue("DEV.QUEUE.3")
    }

    @Bean
    fun mqOutBoundChannel(): MessageChannel {
        return MessageChannels.direct().get()
    }

    @Profile("!test")
    @Bean
    @Throws(NumberFormatException::class, JMSException::class)
    fun jmsTemplate(): JmsTemplate {
        val template = JmsTemplate()
        template.messageConverter = messageConverter
        template.connectionFactory = connectionFactory()
        template.receiveTimeout = 1000
        return template
    }

    @Profile("!test")
    @Bean
    @Throws(NumberFormatException::class, JMSException::class)
    fun connectionFactory(): ConnectionFactory {
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
    fun targetQueue(): Queue {
        return MQQueue("DEV.QUEUE.1")
    }

//    @Bean
//    fun objectMapperBuilder(): Jackson2ObjectMapperBuilder {
//        var builder: Jackson2ObjectMapperBuilder = Jackson2ObjectMapperBuilder()
//        builder.serializationInclusion(JsonInclude.Include.NON_NULL)
//        return builder
//    }

//    @JmsListener(destination = "mqDestn")
//    fun listen(data: String) {
//        logger.info(data)
//    }
}