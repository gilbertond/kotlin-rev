package com.config.integrationflows

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.InboundChannelAdapter
import org.springframework.integration.annotation.Poller
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.core.MessageSource
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.file.FileReadingMessageSource
import org.springframework.integration.file.filters.CompositeFileListFilter
import org.springframework.integration.file.filters.LastModifiedFileListFilter
import org.springframework.integration.file.filters.SimplePatternFileListFilter
import org.springframework.integration.file.transformer.FileToStringTransformer
import org.springframework.messaging.MessageChannel
import java.io.File

@Configuration
open class FileIntegrationFlow{
    /**
     * Adapter to read from file system, needs an inboundChannelAdapter
     * THe adapter is a file reading message resource resp for polling sytem file system directory for files and create a message
     */
    @Bean
    @InboundChannelAdapter(value = "fileInputChannel", poller = [(Poller(fixedDelay = "1000"))/*, (Poller(cron = "0 0 4 ? * * *"))*/])
    open fun messageResource(): MessageSource<File> {

        val compositeFileListFilter = CompositeFileListFilter<File>()  //from spring-integration-file library
        compositeFileListFilter.addFilter(SimplePatternFileListFilter("*.txt"))
        compositeFileListFilter.addFilter(SimplePatternFileListFilter("*.csv")) //Allowed?
        compositeFileListFilter.addFilter(LastModifiedFileListFilter(1000 * 60  * 60 * 24 * 12))

        val fileReadingMessageSource = FileReadingMessageSource(10)
        fileReadingMessageSource.setAutoCreateDirectory(false)
        fileReadingMessageSource.setDirectory(File("/Users/gilberto/workspace/kotlin-rev/rabbit-test/files"))
        fileReadingMessageSource.setFilter(compositeFileListFilter)

        return fileReadingMessageSource
    }

    /**
     * For each polled file, transform content to string before passing to processor
     * Spring provides the transformer
     */
    @Bean
    open fun fileToStringTransformer(): FileToStringTransformer {
        return FileToStringTransformer()
    }


    @Bean
    open fun inBoundFileFlow(): IntegrationFlow{
        return IntegrationFlows.from("fileInputChannel")
                .transform(fileToStringTransformer())
                .handle("fileProcessor", "process")
                .get()
    }

    @Bean
    open fun fileInputChannel(): MessageChannel{
        return DirectChannel()
    }
}