package com.spring.electronics.imports;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class HotFolderService {

    @Value("${file.data.upload-dir}")
    private String IMPORT_DATA_DIR;

    private final ProductImportService productImportService;

    private final CategoryImportService categoryImportService;

    private final UserImportService userImportService;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(); // Use a single thread executor

    @PostConstruct
    public void init() {
        executorService.submit(() -> {
            try {
                monitorHotFolder();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void monitorHotFolder() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(IMPORT_DATA_DIR);

        // Register the folder to monitor both creation and modification events
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

        while (true) {
            WatchKey key = watchService.take(); // Wait for a new or modified file
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.ENTRY_CREATE || kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    Path filePath = (Path) event.context();
                    String fileName = filePath.toString();

                    // Process based on file name, no need to move the file
                    if (fileName.equalsIgnoreCase("products.csv")) {
                        processProductFile(path.resolve(filePath).toString());
                    } else if (fileName.equalsIgnoreCase("categories.csv")) {
                        processCategoryFile(path.resolve(filePath).toString());
                    } else if (fileName.equalsIgnoreCase("users.csv")) {
                        processUserFile(path.resolve(filePath).toString());
                    }
                }
            }
            key.reset(); // Reset key to keep watching
        }
    }

    public void processProductFile(String filePath) {
        try {
            productImportService.importOrUpdateProducts(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processCategoryFile(String filePath) {
        try {
            categoryImportService.importOrUpdateCategories(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void processUserFile(String filePath) {
        try {
            userImportService.importOrUpdateUsers(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
