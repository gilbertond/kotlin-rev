package com.config

import com.ibm.mq.spring.boot.MQConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
//@Profile("!test")
@ConfigurationProperties(
        prefix = "ibm.mq"
)
open class IbmMqConnectionProperties: MQConfigurationProperties() {
    /**
     * Gets the host name.
     *
     * @return Host Name.
     */
    /**
     * Sets the host name.
     *
     * @param hostName Host Name.
     */
    var hostName: String? = null

    /**
     * Gets the port.
     *
     * @return Port Number.
     */
    /**
     * Sets the port.
     *
     * @param port Port Number.
     */
    var port = 0

    /**
     * Gets the channel.
     *
     * @return Channel Name.
     */
    /**
     * Sets the channel.
     *
     * @param channel Channel Name.
     */
//    var channel: String? = null

    /**
     * Gets the queue manager.
     *
     * @return Queue Manager Name.
     */
    /**
     * Sets the queue manager.
     *
     * @param queueManager Queue Manager Name.
     */
//    var queueManager: String? = null

    /**
     * Gets the target queue.
     *
     * @return Target Queue Name.
     */
    /**
     * Sets the target queue.
     *
     * @param targetQueue Target Queue Name.
     */
//    var targetQueue: String? = null

    /**
     * Gets the file path.
     *
     * @return file path.
     */
    /**
     * Sets the file path.
     *
     * @param file file path.
     */
//    var file: String? = null

//    var user: String? = null

//    var password: String? = null

}