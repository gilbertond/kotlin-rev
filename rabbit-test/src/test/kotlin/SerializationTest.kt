import com.domain.Person
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test

open class SerializationTest {
    @Test(expected = JsonMappingException::class)
    open fun givenAbstractClass_whenDeserializing_thenException()
    {
        val json = "{\"person\":{\"id\":\"10\"}}"
        val mapper = ObjectMapper()

        mapper.reader().forType(Person::class.java).readValue<Person>(json)
    }
}