package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传 业务层
 *
 * @author Achen
 */
public interface UploadService {

    /**
     * 头像上传
     *
     * @param img 文件
     * @return
     */
    ResponseResult uploadImg(MultipartFile img);
}
