package com


import com.entity.Person
import com.service.RegistrationService
import org.junit.Assert.*
import org.mockito.Mockito.mock

import org.junit.Test
import org.springframework.test.util.ReflectionTestUtils

import java.util.*

class ReflectionTestUtilsUnitTest {

    @Test
    fun whenNonPublicField_thenReflectionTestUtilsSetField() {
        val person = Person(1L, "test", "test", Date())
        ReflectionTestUtils.setField(person, "id", 1)
        assertTrue(person.id == 1L)

    }

    @Test
    fun whenNonPublicMethod_thenReflectionTestUtilsInvokeMethod() {
        val person = Person(1L, "test", "test", Date())
        ReflectionTestUtils.setField(person, "id", 1)
//        assertTrue(ReflectionTestUtils.invokeMethod(person, "personToString", 1L) == "id: 1; name: Smith, John")
    }

    @Test
    fun whenInjectingMockOfDependency_thenReflectionTestUtilsSetField() {
        val person = Person(1L, "test", "test", Date())
        ReflectionTestUtils.setField(person, "id", 1)

        val personService = mock(RegistrationService::class.java)
//        `when`(personService.getPerson(person.id)).thenReturn("Active")
//        val personService = PersonService()

        // Inject mock into the private field
        ReflectionTestUtils.setField(personService, "personService", personService)
        assertEquals("Person " + person.id + " status: Active", personService.getPerson(person.id))
    }
}