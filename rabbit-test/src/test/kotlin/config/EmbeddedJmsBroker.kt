package config

import com.google.common.io.Files
import com.ibm.mq.jms.MQQueueConnectionFactory
import com.ibm.msg.client.wmq.WMQConstants
import org.apache.activemq.broker.BrokerService
import org.apache.activemq.broker.TransportConnector
//import org.apache.qpid.server.Broker
//import org.apache.qpid.server.BrokerOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.SocketUtils.findAvailableTcpPort
import java.io.IOException
import java.net.URI
import javax.jms.ConnectionFactory
import javax.jms.JMSException
import org.apache.activemq.ActiveMQConnectionFactory
import org.springframework.context.annotation.Profile
import java.util.*
import org.springframework.jms.core.JmsTemplate

@Configuration
@EnableJms
@ActiveProfiles("test")
class EmbeddedJmsBroker {

    /**
     * This represents a JMS server. In this case, this is an embedded JMS server
     */
    @Bean
    @Throws(Exception::class)
    fun createBrokerService(): BrokerService {

        val broker = BrokerService()
        val connector = TransportConnector()

        connector.uri = URI("tcp://localhost:61616")
        broker.addConnector(connector)

        return broker
    }
}