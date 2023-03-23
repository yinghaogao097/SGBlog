package com.sangeng.service.impl;

import com.sangeng.domain.ResponseResult;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.service.UploadService;
import com.sangeng.utils.OSSUtils;
import com.sangeng.utils.PathUtils;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传 业务层处理
 *
 * @author Achen
 */
@Data
@Service
//@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    /**
     * @param img 文件
     * @return
     */
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        // 判断文件类型
        // 获取原始文件名
        String originalFilename = img.getOriginalFilename();
        // 对原始文件名进行判断
        if (!originalFilename.endsWith(".png")) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        // 如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        // 将文件和文件名上传到OSS中 返回生成的文件地址
        String url = OSSUtils.uploadImgOss(img, filePath);
        return ResponseResult.okResult(url);
    }


}
