package com.config.integrationflows

import com.config.MQConfig
import com.config.RabbitConfig
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.amqp.dsl.Amqp
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.jms.dsl.Jms
import org.springframework.jms.core.JmsTemplate
import java.util.logging.Logger

@Configuration
open class OutBoundIntegrationFlow{
    private val logger: Logger = Logger.getLogger(OutBoundIntegrationFlow::class.simpleName)

    @Autowired
    private val rabbitConfig: RabbitConfig ?= null

     @Autowired
      private val connectionFactory: ConnectionFactory?= null

    @Bean
    open fun outBoundFlow(): IntegrationFlow{
        logger.info("*********************\n Creating outBoundChannel to send messages\n *****************")
        return IntegrationFlows .from("outBoundChannel") // or "outBoundChannel" call by name
                .handle(
                        Amqp.outboundAdapter(rabbitConfig!!.rabbitTemplate(connectionFactory = connectionFactory!!))
//                                            .exchangeName("amq.direct") //create exchange
                                .routingKey("gil.queue")//create queue
                )
                .get()
    }



//    @Bean
//    open fun outBoundFlowTest(): IntegrationFlow{
//        logger.info("*********************\n Creating outBoundChannel to send test messages\n *****************")
//        return IntegrationFlows .from(this.rabbitConfig!!.outBoundChannel())
//                .handle(
//                        Amqp.outboundAdapter(rabbitConfig.batchingRabbitTemplate(connectionFactory = connectionFactory!!))
//                                .routingKey("gil.other.queue")//create queue
//                )
//                .get()
//    }
}