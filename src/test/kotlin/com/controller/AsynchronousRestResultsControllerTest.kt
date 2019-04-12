package com.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class AsynchronousRestResultsControllerTest {

    private val MAPPER = ObjectMapper()

    @Autowired
    private val wac: WebApplicationContext? = null

    private var mockMvc: MockMvc? = null

    @Before
    @Throws(Exception::class)
    fun setup() {
        mockMvc = webAppContextSetup(this.wac).build()
    }

    @Test
    @Throws(Exception::class)
    fun testBasic() {
        testSync("/time/basic")
    }

    @Test
    @Throws(Exception::class)
    fun testResponseEntity() {
        testSync("/time/re")
    }

    @Test
    @Throws(Exception::class)
    fun testCallable() {
        testAsync("/time/callable")
    }

    @Test
    @Throws(Exception::class)
    fun testDeferred() {
        testAsync("/time/deferred")
    }


    @Throws(Exception::class)
    private fun testSync(route: String) {
        mockMvc!!.perform(get(route))
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.time").isString)
    }

    @Throws(Exception::class)
    private fun testAsync(route: String) {
        val resultActions = mockMvc!!.perform(get(route))
                .andExpect(request().asyncStarted())
                .andReturn()

        mockMvc!!.perform(asyncDispatch(resultActions))
                .andExpect(status().is2xxSuccessful)
                .andExpect(jsonPath("$.time").isString)
    }
}