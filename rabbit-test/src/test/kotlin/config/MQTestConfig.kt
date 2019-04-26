package config

import com.config.MQConfig
import com.ibm.mq.spring.boot.MQConfigurationProperties
import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.jms.dsl.Jms
import org.springframework.jms.core.JmsTemplate
import java.util.*
import javax.jms.ConnectionFactory
import javax.jms.JMSException

@Configuration
@Profile("test")   
class MQTestConfig{

    val DEFAULT_BROKER_URL = "tcp://localhost:61616"
    val COMMENT_QUEUE = "test-queue"

    @Bean
    @Throws(NumberFormatException::class, JMSException::class)
    fun connectionFactory(): ConnectionFactory {
        val connectionFactory = ActiveMQConnectionFactory()
        connectionFactory.brokerURL = DEFAULT_BROKER_URL
        connectionFactory.trustedPackages = Arrays.asList("com")
        return connectionFactory
    }

    @Bean
    fun jmsTemplate(): JmsTemplate {
        val template = JmsTemplate()
        template.connectionFactory = connectionFactory()
        template.defaultDestinationName = COMMENT_QUEUE
        return template
    }
}

@Profile("test")
@Configuration
class IbmMqConnectionProperties: MQConfigurationProperties()