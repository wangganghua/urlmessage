package com.url.urlmessage.domain.model;

import lombok.Getter;
import lombok.Setter;

public class UpLoadFileResponse {
    @Setter
    @Getter
    private String fileName;
    @Setter
    @Getter
    private String fileDownloadUri;
    @Setter
    @Getter
    private String fileType;
    @Setter
    @Getter
    private long size;

    public UpLoadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }
}
