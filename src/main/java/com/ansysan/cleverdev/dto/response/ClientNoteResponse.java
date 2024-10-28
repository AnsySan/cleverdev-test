package com.ansysan.cleverdev.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientNoteResponse {

    @JsonProperty("comments")
    private String comments;
    @JsonProperty("guid")
    private String guid;
    @JsonProperty("modifiedDateTime")
    private LocalDateTime modifiedDateTime;
    @JsonProperty("clientGuid")
    private String clientGuid;
    @JsonProperty("createdDateTime")
    private LocalDateTime createdDateTime;
    @JsonProperty("loggedUser")
    private String loggedUser;
}
