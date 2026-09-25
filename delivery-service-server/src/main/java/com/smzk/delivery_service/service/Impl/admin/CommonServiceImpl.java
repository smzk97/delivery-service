package com.smzk.delivery_service.service.Impl.admin;

import com.aliyuncs.exceptions.ClientException;
import com.smzk.delivery_service.service.admin.CommonService;
import com.smzk.delivery_service.utils.OssUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class CommonServiceImpl implements CommonService {

    private OssUtils ossUtils;
    @Autowired
    public CommonServiceImpl(OssUtils ossUtils){
        this.ossUtils = ossUtils;
    }

    @Override
    public String fileUpload(MultipartFile file) throws IOException, ClientException {
        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        return ossUtils.upload(inputStream,originalFilename);
    }
}
