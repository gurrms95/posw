package com.omid.osw.test;

import com.omid.osw.common.utils.FileUtils;
import com.omid.osw.common.utils.dto.FileDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FIleController {

    @Autowired
    private FileUtils fileUtils;

    @GetMapping("/test.do")
    public String test(){
        return "file/file";
    }

    @PostMapping("/uploadFile.do")
    public void uploadFile(MultipartFile[] files){
        fileUtils.uploadFiles(files);
    }

    @PostMapping("/downFile.do")
    public void downFile(HttpServletResponse response) throws Exception {
        FileDto dto = new FileDto();
        fileUtils.fileDownLoad(response,dto);
    }

}
