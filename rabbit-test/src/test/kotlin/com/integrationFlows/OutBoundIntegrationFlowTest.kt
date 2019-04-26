package com.integrationFlows

import com.GillApplication
import com.config.RabbitBrokerConnectionProperties
import com.config.RabbitConfig
import com.domain.Person
import com.rabbitmq.client.ConnectionFactory
import config.EmbeddedAMQPBroker
//import config.EmbeddedBroker
import config.MQTestConfig
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.integration.channel.DirectChannel
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import javax.annotation.Resource
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.ClassRule
import org.junit.AfterClass
import org.junit.BeforeClass
import org.apache.activemq.broker.BrokerService


@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(classes = [
    GillApplication::class,
    MQTestConfig::class,
    EmbeddedAMQPBroker::class,
    RabbitConfig::class,
    RabbitBrokerConnectionProperties::class
])
class OutBoundIntegrationFlowTest {

    var embeddedBroker: EmbeddedAMQPBroker? = null

    @Autowired
    lateinit var outBoundChannel: MessageChannel

//    @Resource
//    private val rabbitAdmin: RabbitAdmin? = null

    lateinit var rabbitAdmin: RabbitAdmin

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    @Before
    fun setUp(){
        rabbitAdmin = RabbitAdmin(rabbitTemplate)

        purgerQueue("gil.queue")

        System.out.println("SETUP....." + getQueueCount("gil.queue"))

        assertThat(getQueueCount("gil.queue"), Is(0))

    }

    @After
    fun close() {
        purgerQueue("gil.queue")
    }

    @Test
    fun `test sending messages`() {
        outBoundChannel!!.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        rabbitAdmin!!.purgeQueue("gil.queue", true)
    }

    @Test
    fun `return true if a message in the queue is purged or deleted returns true if messages count is 0`() {
        outBoundChannel!!.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())

        purgerQueue("gil.queue")
        assertThat(getQueueCount("gil.queue"), Is(0))
    }

    @Test
    fun `returns true if count number of message in a queue on rabbitMQ is 3`() {

        outBoundChannel!!.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())

        //TODO add header to message above and may be a test for this header

        Thread.sleep(3000)

        assertThat(getQueueCount("gil.queue"), Is(3))

    }

    private fun getQueueCount(name: String): Int {
        val declareOk = (rabbitAdmin ?: throw NullPointerException("Expression 'rabbitAdmin' must not be null"))
                .rabbitTemplate
                .execute { channel ->
                    channel.queueDeclarePassive(name)
                }
        return declareOk.messageCount
    }

    private fun purgerQueue(name: String): Int {
        val declareOk = (rabbitAdmin ?: throw NullPointerException("Expression 'rabbitAdmin' must not be null"))
                                            .rabbitTemplate
                                            .execute {
                                                channel -> channel.queuePurge(name)                                            }
        return declareOk.messageCount

//        return 1
    }

    private fun getQueueConsumers(name: String): Long {
        val declareOk = (rabbitAdmin ?: throw NullPointerException("Expression 'rabbitAdmin' must not be null"))
                .rabbitTemplate
                .execute { channel -> channel.consumerCount(name) }
        return declareOk
    }

    private fun getQueueConsumersx(name: String): Long {
        val declareOk = (rabbitAdmin ?: throw NullPointerException("Expression 'rabbitAdmin' must not be null"))
                .rabbitTemplate
                .execute { channel -> channel.nextPublishSeqNo }
        return declareOk.plus(10)
    }
}