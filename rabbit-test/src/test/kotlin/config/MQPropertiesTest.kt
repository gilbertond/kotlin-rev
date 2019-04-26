package config

import com.config.IbmMqConnectionProperties
import com.config.MQConfig
import com.ibm.mq.spring.boot.MQAutoConfiguration
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
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.hamcrest.CoreMatchers.`is` as Is


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [
    MQAutoConfiguration::class,
    MQTestConfig::class,
    MQConfig::class,
    IbmMqConnectionProperties::class,
    EmbeddedJmsBroker::class])
@EnableAutoConfiguration
@ActiveProfiles("test")
@TestPropertySource(properties = arrayOf("ibm.mq.queueManager=QMSET",
        "ibm.mq.channel=CHANNELSET",
        "ibm.mq.connName=localhost",
        "ibm.mq.hostName=localhost",
        "ibm.mq.port=61616",
        "ibm.mq.user=USER",
        "ibm.mq.password=PASS",
        "ibm.mq.clientId=mqm",
        "ibm.mq.useIBMCipherMappings=true",
        "ibm.mq.userAuthenticationMQCSP=true",
        "ibm.mq.sslCipherSuite=CIPHER_SUITE",
        "ibm.mq.sslCipherSpec=CIPHER_SPEC"))
class MQPropertiesTest {

    @Autowired
    private lateinit var ibmMqConnectionProperties: IbmMqConnectionProperties

    @Autowired
    private lateinit var mqOutBoundChannel: MessageChannel

    @Test
    fun testConfigs() {
        assertThat(ibmMqConnectionProperties.queueManager, Is("QMSET"))
        assertThat(ibmMqConnectionProperties.channel, Is("CHANNELSET"))
        assertThat(ibmMqConnectionProperties.connName, Is("localhost"))
        assertThat(ibmMqConnectionProperties.user, Is("USER"))
        assertThat(ibmMqConnectionProperties.password, Is("PASS"))
        assertThat(ibmMqConnectionProperties.clientId, Is("mqm"))
        assertThat(ibmMqConnectionProperties.isUseIBMCipherMappings, Is(true))
        assertThat(ibmMqConnectionProperties.isUserAuthenticationMQCSP, Is(true))
        assertThat(System.getProperty("com.ibm.mq.cfg.useIBMCipherMappings"), Is("true"))
        assertThat(ibmMqConnectionProperties.sslCipherSuite, Is("CIPHER_SUITE"))
        assertThat(ibmMqConnectionProperties.sslCipherSpec, Is("CIPHER_SPEC"))
    }

    @Test
    fun `test`() {
        mqOutBoundChannel.send(MessageBuilder.withPayload("some message").build())
    }

    companion object {
        var LOG: Logger = getLogger(MQPropertiesTest::class.java)
    }
}