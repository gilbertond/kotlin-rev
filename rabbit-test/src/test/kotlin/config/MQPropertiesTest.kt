package config

import com.config.ConnectionProperties
import com.config.MQConfig
import com.ibm.mq.spring.boot.MQAutoConfiguration
import com.ibm.mq.spring.boot.MQConfigurationProperties
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.hamcrest.CoreMatchers.`is` as Is


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [MQAutoConfiguration::class, MQConfig::class, ConnectionProperties::class])
@EnableAutoConfiguration
@TestPropertySource(properties = arrayOf("ibm.mq.queueManager=QMSET",
                                        "ibm.mq.channel=CHANNELSET",
                                        "ibm.mq.connName=localhost",
                                        "ibm.mq.user=USER",
                                        "ibm.mq.password=PASS",
                                        "ibm.mq.clientId=mqm",
                                        "ibm.mq.useIBMCipherMappings=true",
                                        "ibm.mq.userAuthenticationMQCSP=true",
                                        "ibm.mq.sslCipherSuite=CIPHER_SUITE",
                                        "ibm.mq.sslCipherSpec=CIPHER_SPEC"))
class MQPropertiesTest{

    @Autowired
    private lateinit var properties: MQConfigurationProperties

    @Autowired
    private lateinit var mqOutBoundChannel: MessageChannel

    @Test
    fun testConfigs() {
        assertThat(properties.queueManager, Is("QMSET"))
        assertThat(properties.channel, Is("CHANNELSET"))
        assertThat(properties.connName, Is("CONNSET"))
        assertThat(properties.user, Is("USER"))
        assertThat(properties.password, Is("PASS"))
        assertThat(properties.clientId, Is("mqm"))
        assertThat(properties.isUseIBMCipherMappings, Is(true))
        assertThat(properties.isUserAuthenticationMQCSP, Is(true))
        assertThat(System.getProperty("com.ibm.mq.cfg.useIBMCipherMappings"), Is("true")  )
        assertThat(properties.sslCipherSuite, Is("CIPHER_SUITE"))
        assertThat(properties.sslCipherSpec, Is("CIPHER_SPEC"))
    }

    @Test
    fun `test`(){
         mqOutBoundChannel.send(MessageBuilder.withPayload("some message").build())
    }

    companion object {
        var LOG: Logger = getLogger(MQPropertiesTest::class.java)
    }
}