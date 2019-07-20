package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author panghu
 * @Title: FileService
 * @ProjectName springboot文件上传下载示例
 * @Description: TODO
 * @date 19-2-22 下午3:27
 */
public interface FileService {

    /**
     * 单文件上传
     * @param file
     * @return
     */
    Map fileUpload(MultipartFile file);

    /**
     * 多文件上传
     * @param files
     * @return
     */
    Map multiFileUpload(List<MultipartFile> files);

    /**
     * 文件下载
     * @param fileName
     * @return
     */
    void download(String fileName);

}
