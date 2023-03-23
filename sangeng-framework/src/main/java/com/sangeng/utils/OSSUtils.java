package com.sangeng.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Component
@ConfigurationProperties(prefix = "oss")
public class OSSUtils {

    private static String AccessKey;
    private static String AccessKeySecret;
    private static String Bucket;

    public void setAccessKey(String accessKey) {
        AccessKey = accessKey;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        AccessKeySecret = accessKeySecret;
    }

    public void setBucket(String bucket) {
        Bucket = bucket;
    }

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private final static String endpoint = "https://oss-cn-beijing.aliyuncs.com";

    private final static String img_url = "https://achen1117.oss-cn-beijing.aliyuncs.com/";//自定义文件域名 映射到服务器上即可

    /**
     * 上传图片到Oss并返回文件地址
     *
     * @param imgFile  文件
     * @param filePath 文件名
     * @return
     */
    public static String uploadImgOss(MultipartFile imgFile, String filePath) {
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = filePath;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, AccessKey, AccessKeySecret);

        try {
            InputStream inputStream = imgFile.getInputStream();
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(Bucket, objectName, inputStream);
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            System.out.println(result.getResponse().getStatusCode());
            return img_url + filePath;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

}