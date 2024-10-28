package com.ansysan.cleverdev.controller;

import com.ansysan.cleverdev.service.DataFromOldSystem;
import com.ansysan.cleverdev.service.ImportFromOldSystemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ImportFromOldSystemController {
    @Value("${app.urlClients}")
    private String urlForClients;
    private final ImportFromOldSystemService importService;
    private final DataFromOldSystem dataFromOldSystem;


    @Scheduled(cron = "* 15 0/2 * * *")
    @PostMapping("/import-from-old-system")
    public ResponseEntity<Void> importFromOldSystem()
    {
        try {
            JSONArray arrayPatientsFromOldSystem = importService.getJsonObjFromOldSystem(urlForClients);
            log.info(importService.importFromOldSystem(arrayPatientsFromOldSystem));
            dataFromOldSystem.setCountNote(0);
        } catch (ResourceAccessException e) {
            log.error("Error connecting to old system");
            log.error(e.getMessage());
        } catch (HttpServerErrorException e) {
            log.error("Parsing error, some key was entered incorrectly");
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}