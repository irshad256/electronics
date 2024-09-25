package com.spring.electronics.imports;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class FileProcessorService implements MessageHandler {

    private static final Logger LOG = Logger.getLogger(FileProcessorService.class.getSimpleName());

    private final ProductImportService productImportService;

    private final CategoryImportService categoryImportService;

    private final UserImportService userImportService;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        LOG.info("Inside FileProcessorService method handleMessage");
        File file = (File) message.getPayload();
        String fileName = file.getName();

        try {
            if (fileName.equalsIgnoreCase("products.txt")) {
                processProductFile(file);
            } else if (fileName.equalsIgnoreCase("categories.txt")) {
                processCategoryFile(file);
            } else if (fileName.equalsIgnoreCase("users.txt")) {
                processUserFile(file);
            }
        } catch (Exception e) {
            e.printStackTrace();  // Handle errors here (log or rethrow)
        }
    }

    private void processProductFile(File file) throws IOException {
        String fileContent = Files.readString(file.toPath());
        productImportService.importOrUpdateProducts(fileContent);
    }

    private void processCategoryFile(File file) throws IOException {
        String fileContent = Files.readString(file.toPath());
        categoryImportService.importOrUpdateCategories(fileContent);
    }

    private void processUserFile(File file) throws IOException {
        String fileContent = Files.readString(file.toPath());
        userImportService.importOrUpdateUsers(fileContent);
    }

}
