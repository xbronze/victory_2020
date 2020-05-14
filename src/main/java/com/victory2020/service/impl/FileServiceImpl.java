package com.victory2020.service.impl;

import com.google.common.collect.Lists;
import com.victory2020.service.IFileService;
import com.victory2020.util.FTPUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1); //后的上传文件的拓展名
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;

        //TODO 改成logger日志记录
        System.out.println("开始上传文件,上传文件的文件名:【" + fileName + "】,上传的路径:【" + path + "】," +
                "新文件名:【" + uploadFileName + "】");

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);

        try{
            file.transferTo(targetFile);
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            targetFile.delete();
        } catch (IOException e) {
            //TODO 改成logger日志记录
            System.out.println("上传文件异常:" + e.getMessage());
            return null;
        }
        return targetFile.getName();
    }

}
