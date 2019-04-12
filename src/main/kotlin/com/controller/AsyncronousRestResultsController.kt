package com.controller

import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import com.model.TimeResponse
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.context.request.async.WebAsyncTask
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger


/*
Tests spring's built in asynchronous capabilities

Spring doesn’t just create a new thread for every request but instead uses a pool with worker threads (named 'http-nio-8080-exec-#') that handle these requests.
By default it has 10 of these workers allowing it to handle 10 requests in parallel
 */

@Slf4j
@RestController
@RequestMapping(value = ["/time"])
class AsynchronousRestResultsController {

    val log = LoggerFactory.getLogger(this::class.java)

    /*
    Assuming this is a very running endpoint with long return from service layer....
    Running this, a single thread handles a request and its response
    After 10 requests runnining at the same time, a new request will get blocked(even if its a fast request) will get blocked/dropped waiting for the previous threads
    Solution is using Callable
     */
    @RequestMapping(value = ["/basic"], method = [RequestMethod.GET])
    fun timeBasic(): TimeResponse {
        log.info("Basic time request")
        return now()
    }

    @RequestMapping(value = ["/re"], method = [RequestMethod.GET])
    fun timeRe(): TimeResponse {
        log.info("Basic time request")
        return now()
    }


    /*
    An easy fix for this is to wrap our response in a Callable. Spring automatically knows that when it receives a callable,
    it should be considered a 'slow' call and should be executed on a different thread
    Callable, spring creates a new thread for each slow thread allowing new requests not to get blocked

    When it receives a Callable from a controller it spins up a new thread to handle it


    Creating threads is relatively expensive and with an unbounded maximum your application server can run out of memory and crash
    This behavior, as well as the name, configured through a WebMvcConfigurer bean
     */
    @RequestMapping(value = ["/callable"], method = [RequestMethod.GET])
    fun timeCallable(): Callable<ResponseEntity<TimeResponse>> {
        log.info("Callable time request")
        return Callable {
            Thread.sleep(5000)
            ResponseEntity.ok(now())
        }
    }

    /*
    DeferredResult from Future allows to determine execution end
    Callable only returns result
     */

    var counter: AtomicInteger = AtomicInteger(1)

    @RequestMapping(value = ["/deferred"], method = [RequestMethod.GET])
    fun timeDeferred(): DeferredResult<ResponseEntity<TimeResponse>> {
        log.info("Deferred time request")
        val result: DeferredResult<ResponseEntity<TimeResponse>> = DeferredResult()

        Thread(
                {
                    Thread.sleep(5000)
                    result.setResult(ResponseEntity.ok(now()))
                }, "MyThread-" + counter.incrementAndGet()
        ).start()

        result.onCompletion {
            println("Long running service finished...")
        }
        return result
    }

    @GetMapping("/custom-timeout-handling")
    @ResponseBody
    fun callableWithCustomTimeoutHandling(): WebAsyncTask<String> {
        val callable = Callable {
            Thread.sleep(2000)
            "Callable result"
        }

        return WebAsyncTask<String>(1000, callable)
    }

    private fun now(): TimeResponse {
        log.info("Creating TimeResponse")
        return TimeResponse(
                LocalDateTime
                        .now()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
    }
}