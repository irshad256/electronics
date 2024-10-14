package com.spring.electronics.imports;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.LastModifiedFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.io.File;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
@EnableIntegration
public class IntegrationConfig {

    @Value("${file.data.upload-dir}")
    private String IMPORT_DATA_DIR;

    private final FileProcessorService fileProcessorService;

    @Bean
    public MessageChannel fileChannel() {
        return new DirectChannel();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        PollerMetadata poller = new PollerMetadata();
        poller.setTrigger(new PeriodicTrigger(Duration.ofSeconds(9000)));
        return poller;
    }

    // Define a message source (file reader) that monitors the hot folder
    @Bean
    public FileReadingMessageSource fileReadingMessageSource() {
        FileReadingMessageSource source = new FileReadingMessageSource();
        source.setDirectory(new File(IMPORT_DATA_DIR));

        // Composite filter to watch for CSV files and recent modifications
        CompositeFileListFilter<File> filters = new CompositeFileListFilter<>();
        filters.addFilter(new SimplePatternFileListFilter("*.impex"));
        filters.addFilter(new LastModifiedFileListFilter()); // Avoid reprocessing unchanged files
        source.setFilter(filters);

        return source;
    }

    @Bean
    public IntegrationFlow fileIntegrationFlow() {
        return IntegrationFlow
                .from(fileReadingMessageSource(), c -> c.poller(poller()))  // Attach the poller here
                .channel(fileChannel())  // Send to the fileChannel
                .handle(fileProcessorService)  // Process the file with fileProcessorService
                .get();
    }

    // Define a service activator for file processing (you'll write the processing logic here)
    @ServiceActivator(inputChannel = "fileChannel")
    @Bean
    public MessageHandler fileProcessor() {
        return fileProcessorService;  // This will contain the logic for processing CSV files
    }

}
