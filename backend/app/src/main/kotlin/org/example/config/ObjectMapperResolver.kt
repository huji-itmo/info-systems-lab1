package org.example.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.ws.rs.ext.ContextResolver
import jakarta.ws.rs.ext.Provider

@Provider
class ObjectMapperResolver : ContextResolver<ObjectMapper> {
    private val objectMapper: ObjectMapper

    init {
        objectMapper =
            ObjectMapper()
                .registerKotlinModule()
                .registerModule(JavaTimeModule())
    }

    override fun getContext(type: Class<*>?): ObjectMapper = objectMapper
}
