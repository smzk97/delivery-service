package com.smzk.delivery_service.service.admin;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface CommonService {
    String fileUpload(MultipartFile file) throws IOException, ClientException;
}
