package org.rabbit.models;

import lombok.Data;

@Data
public class SnappyJobResult {
    private String jobId;
    private String status;
    private String result;
}
