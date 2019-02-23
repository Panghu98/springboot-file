package com.example.demo.service.impl;

import com.example.demo.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author panghu
 * @Title: FileServiceImpl
 * @ProjectName springboot文件上传下载示例
 * @Description: TODO
 * @date 19-2-22 下午3:41
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final static String path =  "/home/panghu/IdeaProjects/springboot文件上传下载示例/src/main/resources/file";

    private HttpServletResponse response;

    @Autowired
    public FileServiceImpl(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public Map fileUpload(MultipartFile file) {
        Map map = new Hashtable();
        if(file.isEmpty()){
            map.put("msg","文件为空");
            return map;
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        File dest = new File(path + "/" + fileName);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            map.put("msg","文件保存成功");
            return map;
        } catch (IllegalStateException e) {
            log.error("error:{}",e.getMessage());
            map.put("msg","失败");
            return map;
        } catch (IOException e) {
            log.error("error:{}",e.getMessage());
            map.put("msg","失败");
            return map;
        }
    }

    @Override
    public Map multiFileUpload(List<MultipartFile> files) {
        /**List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("fileName")
         * filName就是Input表单中的Input*/
        Map map = new Hashtable();
        if(files.isEmpty()){
            map.put("msg","文件为空");
            return map;
        }

        for(MultipartFile file:files){
            String fileName = file.getOriginalFilename();
            int size = (int) file.getSize();
            log.info(fileName + "-->" + size);

            if(file.isEmpty()){
                map.put("msg","文件为空");
                return map;
            }else{
                File dest = new File(path + "/" + fileName);
                //判断文件父目录是否存在
                if(!dest.getParentFile().exists()){
                    dest.getParentFile().mkdir();
                }
                try {
                    file.transferTo(dest);
                }catch (Exception e) {
                    log.error("error:{}",e.getMessage());
                    map.put("msg","失败");
                    return map;
                }
            }
        }
        map.put("msg","文件保存成功");
        return map;

    }

    @Override
    public void download(String fileName) {
        File file = new File(path + "/" + fileName);
        //判断文件父目录是否存在
        Map map = new Hashtable();
        if(file.exists()){
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);

            byte[] buffer = new byte[1024];
            //文件输入流
            FileInputStream fis = null;
            BufferedInputStream bis = null;

            //输出流
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            log.info("文件下载中........");
            try {
                assert bis != null;
                bis.close();
                fis.close();
                assert os != null;
                os.close();
            } catch (IOException e) {
                log.error("error:{}",e.getMessage());
            }
        }
        map.put("msg","下载成功");
        return ;
    }
}
