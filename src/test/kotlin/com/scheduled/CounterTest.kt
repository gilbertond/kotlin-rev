package com.scheduled

import com.Application
import com.config.ScheduleConfig
import org.awaitility.Awaitility.await
import org.awaitility.Duration
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringJUnitConfig(ScheduleConfig::class)
@SpringBootTest(classes = [Application::class])
class CounterTest {

//    @Autowired
    @SpyBean //to check the number of times that the scheduled method is called in the period of one second
    val counter: Counter?=null

    @Test
    fun `scheduledMethod using Awaitility, a asynchronous systems testing api`() {
        await().atMost(Duration.ONE_SECOND).untilAsserted {
            verify(counter, atLeastOnce())?.scheduledMethod()
        }
    }
}