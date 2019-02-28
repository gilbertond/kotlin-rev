package config

import com.config.MQConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.jms.dsl.Jms
import org.springframework.jms.core.JmsTemplate
import javax.jms.ConnectionFactory
import javax.jms.JMSException

@Configuration
open class MQTestConfig{

    @Autowired
    lateinit var connectionFactory: ConnectionFactory

    @Autowired
    lateinit var jmsTemplate: JmsTemplate

//    @Bean
//    @Throws(NumberFormatException::class, JMSException::class)
//    open fun outBoundFlow(): IntegrationFlow {
//
//        MQPropertiesTest.LOG.info("*********************\n Creating outBoundChannel to send messages\n *****************")
//        return IntegrationFlows.from(Jms.outboundAdapter(jmsTemplate) )
//                .extractRequestPayload(true)
//                .destination("some-mq-queue")
//        ).handle("messageHa", "execute").get()
//    }
}