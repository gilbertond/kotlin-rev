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
<<<<<<< HEAD
import com.ibm.mq.headers.pcf.PCFMessage
import com.ibm.mq.headers.pcf.PCFMessageAgent
=======
//import com.ibm.mq.headers.pcf.PCFAgent
import com.ibm.mq.pcf.PCFAgent
import com.ibm.mq.headers.pcf.PCFMessage
import com.ibm.mq.headers.pcf.PCFMessageAgent
import java.lang.Exception
>>>>>>> fixed json config bugs in rabbit module


@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
@EnableCaching
open class GillApplication : CommandLineRunner {
    @Autowired
    val rabbitConfig: RabbitConfig? = null

    @Autowired
    val outBoundChannel: MessageChannel? = null
<<<<<<< HEAD

    @Autowired
    lateinit var mqOutBoundChannel: MessageChannel

    @Autowired
=======

    @Autowired
    lateinit var mqOutBoundChannel: MessageChannel

    @Autowired
>>>>>>> fixed json config bugs in rabbit module
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
<<<<<<< HEAD
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
=======

//        THIS IS FOR AMQ SENDING....
//        try {
//            mqOutBoundChannel.send(MessageBuilder.withPayload(
//                    Person(
//                            999,
//                            "Am i there$count",
//                            "let us see $count"
//                    )).setHeader("person", "person$count").build()
//            )
//        }catch (ex: Exception){
//            ex.printStackTrace()
//        }
>>>>>>> fixed json config bugs in rabbit module
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

<<<<<<< HEAD
//        val agent = PCFMessageAgent("localhost", 1414, "SYSTEM.DEF.SVRCONN")
//        val pcfCmd = PCFMessage(MQConstants.MQCMD_INQUIRE_CHANNEL)
//        pcfCmd.addParameter(MQConstants.MQCACH_CHANNEL_NAME, "mqOutBoundChannel")
//        val pcfResponse = agent.send(pcfCmd)
=======
        try {
//            val agent = PCFAgent("localhost", 1414, "DEV.ADMIN.SVRCONN")
////            MQCFH cfh = new MQCFH(agentNode.send(CMQCFC.MQCMD_INQUIRE_Q, {new MQCFST(CMQC.MQCA_Q_NAME, QUEUE_NAME)})[0]);

//            val pcfCmd = PCFMessage(MQConstants.MQCMD_INQUIRE_CHANNEL)
//            pcfCmd.addParameter(MQConstants.MQCACH_CHANNEL_NAME, "mqOutBoundChannel")
//            val pcfResponse = agent.send(pcfCmd)
        }catch (ex: Exception){
            ex.printStackTrace()
        }
>>>>>>> fixed json config bugs in rabbit module
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(GillApplication::class.java, *args)
}
