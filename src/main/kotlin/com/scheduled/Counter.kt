package com.scheduled

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Counter{

    @Scheduled(fixedRate = 40000)
    fun scheduledMethod(){
        println("Am running...")
    }
}