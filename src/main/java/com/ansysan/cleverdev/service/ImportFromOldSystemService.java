package com.ansysan.cleverdev.service;

import com.ansysan.cleverdev.entity.Patient;
import com.ansysan.cleverdev.entity.PatientStatus;
import com.ansysan.cleverdev.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportFromOldSystemService {

    long timeBegin = System.currentTimeMillis();
    ParameterizedTypeReference<JSONArray> typeRef = new ParameterizedTypeReference<>() {
    };
    private final PatientRepository patientRepository;
    private final DataFromOldSystem dataFromOldSystem;

    @Value("${app.urlNotes}")
    private String urlForNotes;
    @Value("${app.importData.dateFrom}")
    private String dateFrom;
    @Value("${app.importData.dateTo}")
    private String dateTo;

    public JSONArray getJsonObjFromOldSystem(String urlClient) {
        log.debug("get json from old system");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<JSONObject> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSONArray> response = restTemplate.exchange(urlClient, HttpMethod.POST, entity, typeRef);
        return response.getBody();
    }

    public List<Patient> getPatientFromRepository() {
        log.debug("Get patient from repository");
        return patientRepository.findAll();
    }

    public void getPatientWithStatusActive(Patient patient, JSONArray objectFromOldSystem) {
        log.debug("Get patient with patient status");
        if (PatientStatus.ACTIVE == (patient.getStatusId())) {
            String[] listGuid = patient.getOldClientGuid().split(",");
            for (String guid : listGuid) {
                LinkedHashMap jsonPatientKey;
                for (Object ob : objectFromOldSystem) {
                    jsonPatientKey = (LinkedHashMap) ob;
                    if (guid.equals(jsonPatientKey.get("guid").toString())) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        JSONObject request = new JSONObject();
                        request.put("agency", jsonPatientKey.get("agency"));
                        request.put("dateFrom", dateFrom);
                        request.put("dateTo", dateTo);
                        request.put("clientGuid", jsonPatientKey.get("guid"));
                        RestTemplate restTemplate = new RestTemplate();
                        HttpEntity<JSONObject> entity = new HttpEntity<>(request, headers);
                        ResponseEntity<JSONArray> response = restTemplate.exchange(urlForNotes, HttpMethod.POST, entity, typeRef);
                        JSONArray responseDetailsNotes = response.getBody();

                        if (responseDetailsNotes.length() == 0) {
                            continue;
                        } else {
                            dataFromOldSystem.saveNoteInDB(responseDetailsNotes, patient);
                        }
                        break;
                    }
                }
            }
        }
    }


    public String importFromOldSystem(JSONArray getObjFromOldSystem) {
        log.debug("Import from old system");
        for (Patient patient : getPatientFromRepository()) {
            getPatientWithStatusActive(patient, getObjFromOldSystem);
        }
        long timeEnd = System.currentTimeMillis();
        long actualTime = timeEnd - timeBegin;
        return "Added notes: " + dataFromOldSystem.getCountNote() +
                "\n time passed: " + actualTime;
    }

}
