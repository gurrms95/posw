package com.omid.osw.common.utils.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class FileDto {

    String fileRealNm;
    String fileTempNm;
    String filePath;
    String uploadDate;
    String uploadUser;
    String fileExten;
    String fileParent;
}
