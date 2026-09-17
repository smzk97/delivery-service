package com.smzk.delivery_service.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public interface CommonService {
    String fileUpload(MultipartFile file) throws IOException, ClientException;
}
