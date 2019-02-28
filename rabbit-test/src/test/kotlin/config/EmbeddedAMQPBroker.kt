package config

import com.google.common.io.Files
import org.apache.qpid.server.Broker
import org.apache.qpid.server.BrokerOptions
import org.springframework.util.SocketUtils.findAvailableTcpPort
import java.io.IOException

class EmbeddedAMQPBroker{
    private val BROKER_PORT = findAvailableTcpPort()
    private val broker = Broker()
    
    @Throws(Exception::class)
    fun EmbeddedAMQPBroker(){
        val configFileName = "rabbit_gil-rabbit-host.json"
        val credentialsFile = "application.properties"

        // prepare options
        val brokerOptions = BrokerOptions()
        brokerOptions.setConfigProperty("qpid.amqp_port", BROKER_PORT.toString())
        brokerOptions.setConfigProperty("qpid.pass_file", findResourcePath(credentialsFile))
        brokerOptions.setConfigProperty("qpid.work_dir", Files.createTempDir().absolutePath)
        brokerOptions.initialConfigurationLocation = findResourcePath(configFileName)

        // start broker
        broker.startup(brokerOptions)
    }

    @Throws(IOException::class)
    private fun findResourcePath(file: String): String {
        // get the configuration file in the classpath
        return ""
    }
}