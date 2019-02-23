package com.example.demo.controller;

import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author panghu
 * @Title: FileController
 * @ProjectName springboot文件上传下载示例
 * @Description: TODO
 * @date 19-2-22 下午3:21
 */
@Controller
public class FileController {

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 获取file.html页面
     */
    @RequestMapping("file")
    public String file() {
        return "/file";
    }

    /**
     * 实现文件上传
     */
    @RequestMapping("/fileUpload")
    @ResponseBody
    public Map fileUpload(@RequestParam("fileName")MultipartFile file) {
        return fileService.fileUpload(file);
    }

    /**
     * 获取multiFile.html页面
     */
    @RequestMapping("multiFile")
    public String multiFile() {
        return "/multiFile";
    }

    /**
     * 实现多文件上传
     */
    @RequestMapping("/multiFileUpload")
    public @ResponseBody Map multiFileUpload(List<MultipartFile> files) {
       return fileService.multiFileUpload(files);
    }


    @GetMapping("/download")
    @ResponseBody
    public void downLoad(String fileName){
        fileService.download(fileName);
    }

}
