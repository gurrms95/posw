package com.omid.osw.common.utils;

import com.omid.osw.common.utils.dto.FileDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class FileUtils {
    @Value("${spring.servlet.multipart.location}")
    private String filePath;

    public List<FileDto> uploadFiles(MultipartFile[] files){
        List<FileDto> list = new ArrayList<>();

        //파일이 없으면 empty Dto 반환
        if(files[0].isEmpty()){
            return list;
        }

        //디렉토리 없으면 새로생성
        File dir = new File(filePath);
        if(!dir.exists()){
            dir.mkdir();
        }

        //파일정보 저장 및 파일 업로드
        for(MultipartFile file : files){
            try {
                FileDto dto = new FileDto();
                dto.setFilePath(filePath);
                dto.setFileExten(getExtension(file.getOriginalFilename()));
                dto.setFileRealNm(file.getOriginalFilename());
                dto.setFileTempNm(getRandomString()+getExtension(file.getOriginalFilename()));
                dto.setUploadDate(getTodayDate());

                File uFile = new File(dto.getFilePath(), dto.getFileTempNm());
                file.transferTo(uFile);

                list.add(dto);

            } catch (Exception e){
                //Exception처리 클래스 넣을 예정
                log.info(e.getMessage());
            }
        }

        return list;
    }

    public void fileDownLoad(HttpServletResponse response, FileDto dto) throws Exception {
        try {
            File dFile = new File(filePath,dto.getFileTempNm());

            if(dFile.length() > 0) {

                String encodeFileName = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(dto.getFileRealNm(), "UTF-8");

                response.setContentType("application/octet-stream; charset=utf-8");
                response.setHeader("Content-Disposition", encodeFileName);
                response.setContentLengthLong(dFile.length());

                BufferedInputStream in = null;
                BufferedOutputStream out = null;

                in = new BufferedInputStream(new FileInputStream(dFile));
                out = new BufferedOutputStream(response.getOutputStream());

                try {
                    byte[] buffer = new byte[4096];
                    int bytesRead = 0;

                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    out.flush();
                } finally {
                    in.close();
                    out.close();
                }
            } else {
                throw new FileNotFoundException("파일이 없습니다.");
            }
        } catch (Exception e){
            log.info(e.getMessage());
        }
    }

    public void fileZipDownLoad(FileDto dto){

    }

    private String getRandomString(){
        return UUID.randomUUID().toString();
    }

    private String getExtension(String fileNm){
        return fileNm.substring(fileNm.lastIndexOf("."));
    }

    private String getTodayDate(){
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return now.format(formatter);
    }
}
