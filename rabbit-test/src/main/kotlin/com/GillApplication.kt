package com

import com.config.RabbitConfig
import com.dao.RedisRepo
import com.domain.Person
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.config.EnableIntegration
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import com.ibm.mq.constants.MQConstants
import com.ibm.mq.headers.pcf.PCFMessage
import com.ibm.mq.headers.pcf.PCFMessageAgent


@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
@EnableCaching
open class GillApplication : CommandLineRunner {
    @Autowired
    val rabbitConfig: RabbitConfig? = null

    @Autowired
    val outBoundChannel: MessageChannel? = null

    @Autowired
    lateinit var mqOutBoundChannel: MessageChannel

    @Autowired
    lateinit var redisRepo: RedisRepo

    @Autowired
    lateinit var mapper: ObjectMapper

    //Send a message after startup
    override fun run(vararg args: String?) {

        var count = 0
//        while (count < 2) { count++
//            outBoundChannel!!.send(MessageBuilder.withPayload(Person(12, "Gilbertxx$count", "Ndenzi$count")).setHeader("person", "person$count").build())
//            outBoundChannel.send(MessageBuilder.withPayload(Person(34, "Albertxx$count", "Gabiro$count")).setHeader("person", "person$count").build())
//            outBoundChannel.send(MessageBuilder.withPayload(Person(92, "Godwin$count", "Mugabe$count")).setHeader("person", "person$count").build())
//            outBoundChannel.send(MessageBuilder.withPayload(Person(323, "Kabera$count", "Dunstan$count")).setHeader("person", "person$count").build())
//            outBoundChannel.send(MessageBuilder.withPayload(Person(22, "Karen$count", "Uwera$count")).setHeader("person", "person$count").build())
//
////            Thread.sleep(2000)
//            outBoundChannel.send(MessageBuilder.withPayload(Person(82, "Bonita$count", "Abera$count")).setHeader("person", "person$count").build())
//            outBoundChannel.send(MessageBuilder.withPayload(Person(353, "Renita$count", "Ingabire$count")).setHeader("person", "person$count").build())
//            outBoundChannel.send(MessageBuilder.withPayload(Person(353, "Renita$count", "Ingabire$count")).setHeader("person", "person$count").build())
//            outBoundChannel.send(MessageBuilder.withPayload(Person(423, "Daiel$count", "Ndamaje$count")).setHeader("person", "person$count").build())
//            outBoundChannel.send(MessageBuilder.withPayload(Person(423, "Daiel$count", "Ndamaje$count")).setHeader("person", "person$count").build())
//
////            Thread.sleep(2000)
        try {
            mqOutBoundChannel.send(MessageBuilder.withPayload(
                    Person(
                            999,
                            "Am i there$count",
                            "let us see $count"
                    )).setHeader("person", "person$count").build()
            )
        }catch (ex: Exception){
            ex.printStackTrace()
        }
//        mqOutBoundChannel.send(
//                MessageBuilder.withPayload(
//                        Person(
//                                999,
//                                "Am i there$count",
//                                "let us see $count"
//                        )).setHeader("person", "person$count").build()
//        )
//        mqOutBoundChannel.send(
//                MessageBuilder.withPayload(
//                        Person(999, "Am i there$count", "let us see $count")).setHeader("person", "person$count").build()
//        )
//        mqOutBoundChannel.send(
//                MessageBuilder.withPayload(
//                        Person(999, "Am i there$count", "let us see $count")).setHeader("person", "person$count").build()
//        )
//        mqOutBoundChannel!!.send(
//                MessageBuilder.withPayload(
//                        Person(
//                                999,
//                                "Am i there$count",
//                                "let us see $count"
//                        )).setHeader("person", "person$count").build()
//        )


//        }

//        System.out.println("Redis: ${mapper.writerWithDefaultPrettyPrinter().writeValueAsString(redisRepo.findAll())}")

//        val agent = PCFMessageAgent("localhost", 1414, "SYSTEM.DEF.SVRCONN")
//        val pcfCmd = PCFMessage(MQConstants.MQCMD_INQUIRE_CHANNEL)
//        pcfCmd.addParameter(MQConstants.MQCACH_CHANNEL_NAME, "mqOutBoundChannel")
//        val pcfResponse = agent.send(pcfCmd)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(GillApplication::class.java, *args)
}
