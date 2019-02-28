package integration

import com.google.common.io.Files
import org.apache.qpid.server.Broker
import org.apache.qpid.server.BrokerOptions
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
    @Value("\${spring.rabbitmq.port}")
    private val rabbitmqPort: String? = null


    val QPID_CONFIG_LOCATION = "src/test/resources/qpid-config.json"
    val APPLICATION_CONFIG_LOCATION = "src/test/resources/application.properties"

    @MockBean
    private val runner: Runner? = null

//    @Autowired
//    private val rabbitTemplate: RabbitTemplate? = null

//    @Autowired
//    private val firstReceiver: FirstReceiver? = null
//
//    @Autowired
//    private val secondReceiver: SecondReceiver? = null

    //TODO Extract external util class (Start(Stop Rabbitmq )
//    @ClassRule
    open val resource: ExternalResource = object : ExternalResource() {
        private val broker = Broker()

        @Throws(Throwable::class)
        override fun before() {
            val properties = Properties()
            properties.load(FileInputStream(File(APPLICATION_CONFIG_LOCATION)))
            val amqpPort = properties.getProperty("spring.rabbitmq.port")
            val tmpFolder = Files.createTempDir()
            val userDir = System.getProperty("user.dir").toString()
            val file = File(userDir)
            val homePath = file.getAbsolutePath()
            val brokerOptions = BrokerOptions()
            brokerOptions.setConfigProperty("qpid.work_dir", tmpFolder.getAbsolutePath())
            brokerOptions.setConfigProperty("qpid.amqp_port", amqpPort)
            brokerOptions.setConfigProperty("qpid.home_dir", homePath)
            brokerOptions.initialConfigurationLocation = homePath + "/" + QPID_CONFIG_LOCATION
            broker.startup(brokerOptions)
        }


        override fun after() {
            broker.shutdown()
        }

    }


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