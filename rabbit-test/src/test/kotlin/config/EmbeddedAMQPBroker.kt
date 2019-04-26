package config

import com.google.common.io.Files
import org.apache.qpid.server.Broker
import org.apache.qpid.server.BrokerOptions
//import org.apache.qpid.server.Broker
//import org.apache.qpid.server.BrokerOptions
//import org.junit.Before
import org.junit.ClassRule
import org.junit.rules.ExternalResource
import org.junit.runner.RunWith
import org.junit.runner.Runner
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.util.SocketUtils.findAvailableTcpPort
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

//@RunWith(SpringJUnit4ClassRunner::class)
@Configuration
open class EmbeddedAMQPBroker {
    @Value("\${spring.rabbitmq.port}")
    private val rabbitmqPort: String? = null

    @Value("\${spring.rabbitmq.virtual-host}")
    private val virtualHost: String? = null

    private val broker = Broker()

    var amqpPort = "5672"

    var qpidHomeDir = ""
    var configFileName = "src/test/resources/qpid-config.json"


    @Profile("test")
    @Bean
    @Throws(Exception::class)
    fun broker(): Broker {
        broker.startup(brokerOptions())
        return broker
    }


    @Profile("test")
    @Bean
    fun brokerOptions(): BrokerOptions {

        val tmpFolder = Files.createTempDir()

        //small hack, because userDir is not same when running Application and ApplicationTest
        //it leads to some issue locating the files after, so hacking it here
        var userDir = System.getProperty("user.dir").toString()
        if (!userDir.contains(qpidHomeDir)) {
            userDir = userDir + File.separator + qpidHomeDir
        }

        val file = File(userDir)
        val homePath = file.absolutePath

        val brokerOptions = BrokerOptions()

        brokerOptions.setConfigProperty("qpid.work_dir", tmpFolder.absolutePath)
        brokerOptions.setConfigProperty("qpid.amqp_port", amqpPort)
        brokerOptions.setConfigProperty("qpid.home_dir", homePath)
        brokerOptions.initialConfigurationLocation = "$homePath/$configFileName"

        return brokerOptions
    }
}