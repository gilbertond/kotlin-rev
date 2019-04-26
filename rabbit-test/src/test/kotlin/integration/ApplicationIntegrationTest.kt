package integration

import com.google.common.io.Files
//import org.apache.qpid.server.Broker
//import org.apache.qpid.server.BrokerOptions
import org.junit.ClassRule
import org.junit.rules.ExternalResource
import org.junit.runner.RunWith
import org.junit.runner.Runner
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.io.File
import java.io.FileInputStream
import java.util.*

//@RunWith(SpringRunner::class)
//@SpringBootTest
class ApplicationIntegrationTest{


//    @Autowired
//    private val rabbitTemplate: RabbitTemplate? = null

//    @Autowired
//    private val firstReceiver: FirstReceiver? = null
//
//    @Autowired
//    private val secondReceiver: SecondReceiver? = null

    //TODO Extract external util class (Start(Stop Rabbitmq )



//    @Test
//    @Throws(Exception::class)
//    fun testWithFirstReceiverRoutingKey() {
//
//        rabbitTemplate.expectedQueueNames()
//        firstReceiver!!.initCounter()
//        secondReceiver!!.initCounter()
//        rabbitTemplate!!.convertAndSend(EXCHANGE_NAME, FirstReceiver.QUEUE_ROUTINGKEY, "Hello from RabbitMQ Sent 1!")
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, FirstReceiver.QUEUE_ROUTINGKEY, "Hello from RabbitMQ Sent 2!")
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, FirstReceiver.QUEUE_ROUTINGKEY, "Hello from RabbitMQ Sent 3!")
//        Thread.sleep(5000)
//        assertThat(firstReceiver!!.getCounter()).isEqualTo(3)
//        assertThat(secondReceiver!!.getCounter()).isEqualTo(0)
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testMixReceiverRoutingKey() {
//        //TODO CREATE @ RULE TO BE ABLE TO INIT COUNTERS
//        firstReceiver!!.initCounter()
//        secondReceiver!!.initCounter()
//        rabbitTemplate!!.convertAndSend(EXCHANGE_NAME, FirstReceiver.QUEUE_ROUTINGKEY, "Hello from RabbitMQ Sent 1!")
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, SecondReceiver.QUEUE_ROUTINGKEY, "Hello from RabbitMQ Sent 2!")
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, FirstReceiver.QUEUE_ROUTINGKEY, "Hello from RabbitMQ Sent 3!")
//        Thread.sleep(5000)
//        assertThat(firstReceiver!!.getCounter()).isEqualTo(2)
//        assertThat(secondReceiver!!.getCounter()).isEqualTo(1)
//    }
//
//    @Test
//    @Throws(Exception::class)
//    fun testNoRoutingkey() {
//        firstReceiver!!.initCounter()
//        secondReceiver!!.initCounter()
//        rabbitTemplate!!.convertAndSend(EXCHANGE_NAME, "routing_not_found", "Hello from RabbitMQ Sent 1!")
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "routing_not_found", "Hello from RabbitMQ Sent 2!")
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "routing_not_found", "Hello from RabbitMQ Sent 3!")
//        Thread.sleep(5000)
//        assertThat(firstReceiver!!.getCounter()).isEqualTo(0)
//        assertThat(secondReceiver!!.getCounter()).isEqualTo(0)
//    }
}