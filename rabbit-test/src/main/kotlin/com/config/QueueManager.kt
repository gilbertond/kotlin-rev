package com.config

import com.ibm.mq.MQQueueManager
import com.ibm.mq.MQC
import com.ibm.mq.MQEnvironment
import com.ibm.mq.MQException
import com.ibm.mq.constants.MQConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class QueueManager{

    @Autowired
    private lateinit var connectionProperties: ConnectionProperties

    val manager: String? = null
    var qmgr: MQQueueManager? = null

//    TODO register me as a bean for singleton purposes
    @Throws(MQException::class)
    private fun createQueueManager(): MQQueueManager {
        MQEnvironment.channel = connectionProperties.channel
        MQEnvironment.port = connectionProperties.port
        MQEnvironment.hostname = connectionProperties.hostName
        MQEnvironment.userID = connectionProperties.user
        MQEnvironment.password = connectionProperties.password
        connectionProperties.queueManager = connectionProperties.queueManager

        MQEnvironment.properties[MQC.TRANSPORT_PROPERTY] = MQC.TRANSPORT_MQSERIES
        return MQQueueManager(manager)
    }

    @Throws(MQException::class)
    fun depthOf(queueName: String): Int {

        qmgr = createQueueManager()

        val queue = qmgr!!.accessQueue(
                queueName,
                MQConstants.MQOO_INQUIRE or MQConstants.MQOO_INPUT_AS_Q_DEF,
                connectionProperties.queueManager,
                null,
                null
        )
//        queue.close() //Close the queue after otherwise multiple connections
        return queue.currentDepth
    }
}