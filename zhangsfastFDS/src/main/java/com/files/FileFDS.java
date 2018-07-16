package com.files;

import org.csource.fastdfs.*;

/**
 * Create with www.dezhe.com
 *
 * @Author 德哲
 * @Date 2018/7/16 15:51
 */
public class FileFDS {

    public static void main(String[] args) throws Exception {
        // 七步骤
        // 1、加载配置文件，配置文件中的内容就是 tracker 服务器地址。ClientGlobal加载
        ClientGlobal.init("D:\\itheima\\project\\zhangsfastFDS\\src\\main\\resources\\fdfs_client.conf");
        // 2、创建一个TrackClient对象，直接new 一个
        TrackerClient trackerClient = new TrackerClient();
        // 3、使用 trackClient 对象获取连接，获得一个trackServer 对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 4、创建一个StorageServer的引用，值为null
        StorageServer storageServer = null;
        // 5、创建一个StorageClinet对象，需要两个参数，TrackerServer对象，和StorageServer的引用。
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 6、使用storageClient对象上传图片
        String[] strings = storageClient.upload_file("E:\\girl.jpg", "jpg", null);
        for (String string : strings) {
            System.out.println(string);
        }
        // 结果
        // 192.168.25.133/group1/M00/00/00/wKgZhVtMVACAAWDBAA7sGmpR00s524.jpg
    }

}
