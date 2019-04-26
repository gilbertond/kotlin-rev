package com.config.service.queueMonitor

import com.config.QueueManager
import com.domain.Person
import com.rabbitmq.http.client.Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder

@Configuration
class MonitorQueue {

    @Autowired
    lateinit var outBoundChannel: MessageChannel

    @Autowired
    lateinit var mqOutBoundChannel: MessageChannel

    @Autowired
    lateinit var queueManager: QueueManager

    var count = 0

    fun monitorRabbit() {

//        while (count < 2) { count++
//        ---------Send message to rabbit --------
        outBoundChannel.send(MessageBuilder.withPayload(Person(12, "Gilbertxx$count", "Ndenzi$count")).setHeader("person", "person$count").build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(34, "Albertxx$count", "Gabiro$count")).setHeader("person", "person$count").build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(92, "Godwin$count", "Mugabe$count")).setHeader("person", "person$count").build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(323, "Kabera$count", "Dunstan$count")).setHeader("person", "person$count").build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(22, "Karen$count", "Uwera$count")).setHeader("person", "person$count").build())

//            Thread.sleep(2000)
        outBoundChannel.send(MessageBuilder.withPayload(Person(82, "Bonita$count", "Abera$count")).setHeader("person", "person$count").build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(353, "Renita$count", "Ingabire$count")).setHeader("person", "person$count").build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(353, "Renita$count", "Ingabire$count")).setHeader("person", "person$count").build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(423, "Daiel$count", "Ndamaje$count")).setHeader("person", "person$count").build())
        outBoundChannel.send(MessageBuilder.withPayload(Person(423, "Daiel$count", "Ndamaje$count")).setHeader("person", "person$count").build())


        // -------Rabbit Queue depth ------------
        try {
            val c = Client("http://127.0.0.1:8585/api/", "gilberto", "gil")

//           get overview
            c.overview

//          list cluster nodes
            c.nodes
//
// get status and metrics of individual node
            c.getNode("rabbit@mercurio.local")

//             list client connections
            c.connections

            // list all queues
            c.queues


        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun monitorWMQ() {
        /* --------- Send message to WMQ ---------  */
        mqOutBoundChannel.send(MessageBuilder.withPayload(
                Person(
                        999,
                        "Am i there$count",
                        "let us see $count"
                )).setHeader("person", "person$count").build()
        )
        mqOutBoundChannel.send(
                MessageBuilder.withPayload(
                        Person(
                                999,
                                "Am i there$count",
                                "let us see $count"
                        )).setHeader("person", "person$count").build()
        )
        mqOutBoundChannel.send(
                MessageBuilder.withPayload(
                        Person(999, "Am i there$count", "let us see $count")).setHeader("person", "person$count").build()
        )
        mqOutBoundChannel.send(
                MessageBuilder.withPayload(
                        Person(999, "Am i there$count", "let us see $count")).setHeader("person", "person$count").build()
        )
        mqOutBoundChannel!!.send(
                MessageBuilder.withPayload(
                        Person(
                                999,
                                "Am i there$count",
                                "let us see $count"
                        )).setHeader("person", "person$count").build()
        )

        /* ----------- WMQ Queue depth -----------  */
        try {
            println(
                    "Depth: ${queueManager.depthOf("DEV.QUEUE.3")}"
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}