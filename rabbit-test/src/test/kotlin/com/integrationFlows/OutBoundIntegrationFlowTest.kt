package com.integrationFlows

import com.GillApplication
import com.domain.Person
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import javax.annotation.Resource
import org.hamcrest.CoreMatchers.`is` as Is


@Ignore
@ActiveProfiles("integration", "local")
@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest (classes = [GillApplication::class])
class OutBoundIntegrationFlowTest{

    @Autowired
    lateinit var outBoundChannel: MessageChannel

    @Resource
    private val admin: RabbitAdmin? = null

    @Before
    fun setUp(){
        purgerQueue("gil.other.queue")

        System.out.println("SETUP....." + getQueueCount("gil.other.queue"))

        assertThat(getQueueCount("gil.other.queue"), Is(0))

    }

    @After
    fun close(){
        purgerQueue("gil.other.queue")
    }

    @Test
    fun `test sending messages`(){
        outBoundChannel!!.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        admin!!.purgeQueue("gil.other.queue", true)
     }

    @Test
    fun `test received messages are the same sent`(){

    }

    @Test
    fun `return true if a message in the queue is purged or deleted returns true if messages count is 0`(){
        outBoundChannel!!.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())

        purgerQueue("gil.other.queue")
        assertThat(getQueueCount( "gil.other.queue"), Is(0))
    }

    @Test
    fun `returns true if count number of message in a queue on rabbitMQ is 3`(){

        outBoundChannel!!.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(12, "Gilbert", "Ndenzi")).build())

        //TODO add header to message above and may be a test for this header

        Thread.sleep(3000)

        assertThat(getQueueCount("gil.other.queue"), Is(3))

    }
    
    private fun getQueueCount(name: String): Int {
        val declareOk = (admin ?: throw NullPointerException("Expression 'admin' must not be null"))
                                            .rabbitTemplate
                                            .execute {
                                                channel -> channel.queueDeclarePassive(name)
                                            }
        return declareOk.messageCount
    }

    private fun purgerQueue(name: String): Int {
//        val declareOk = (admin ?: throw NullPointerException("Expression 'admin' must not be null"))
//                                            .rabbitTemplate
//                                            .execute {
//                                                channel -> channel.queuePurge(name)                                            }
//        return declareOk.messageCount

        return 1
    }

    private fun getQueueConsumers(name: String): Long {
        val declareOk = (admin ?: throw NullPointerException("Expression 'admin' must not be null"))
                .rabbitTemplate
                .execute {
                    channel -> channel.consumerCount(name)                                           }
        return declareOk
    }

    private fun getQueueConsumersx(name: String): Long {
        val declareOk = (admin ?: throw NullPointerException("Expression 'admin' must not be null"))
                .rabbitTemplate
                .execute {
                    channel -> channel.nextPublishSeqNo                                          }
        return declareOk.plus(10)
    }
}