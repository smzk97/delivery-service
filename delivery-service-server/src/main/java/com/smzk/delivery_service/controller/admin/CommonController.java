package com.smzk.delivery_service.controller.admin;

import com.aliyuncs.exceptions.ClientException;
import com.smzk.delivery_service.entity.admin.Result;
import com.smzk.delivery_service.service.admin.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/common")
public class CommonController {

    private CommonService commonService;
    @Autowired
    public CommonController(CommonService commonService){
        this.commonService = commonService;
    }

    @PostMapping("/upload")
    public Result fileUpload(MultipartFile file) throws IOException, ClientException {
        String url = commonService.fileUpload(file);
        return Result.Success(url);
    }

}
