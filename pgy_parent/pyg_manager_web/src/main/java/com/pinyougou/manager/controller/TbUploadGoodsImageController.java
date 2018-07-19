package com.pinyougou.manager.controller;

import com.pinyougou.utils.FastDFSClient;
import domaincommon.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/16 19:36
 */
@RestController
@RequestMapping("/upload")
public class TbUploadGoodsImageController {

    /**
     * 添加文件服务器的IP地址
     */
    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    /**
     * 点击上传功能
     * @param file
     * @return
     */
    @RequestMapping("/uploadFile")
    public Result getUploadImages(MultipartFile file) {
        try {
            // 使用工具类 映射到图片服务器的IP
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fdfs_client.conf");
            // 获取文件的真实名称
            String originalFilename = file.getOriginalFilename();
            // 进行对文件的截取，获取文件的后缀名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            // 进行文件下载
            String fileUrl = fastDFSClient.uploadFile(file.getBytes(), extName);
            return new Result(true,FILE_SERVER_URL+fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传失败");
        }
    }

}
