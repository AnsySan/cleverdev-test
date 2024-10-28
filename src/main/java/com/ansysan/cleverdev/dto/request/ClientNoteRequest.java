package com.ansysan.cleverdev.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientNoteRequest {
    @JsonProperty("agency")
    private String agency;
    @JsonProperty("dateFrom")
    private LocalDate dateFrom;
    @JsonProperty("dateTo")
    private LocalDate dateTo;
    @JsonProperty("clientGuid")
    private String clientGuid;
}
