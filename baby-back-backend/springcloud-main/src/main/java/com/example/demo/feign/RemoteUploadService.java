package com.example.demo.feign;

import com.example.demo.entity.ResponseBase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "springcloud-file")
public interface RemoteUploadService {
    @PostMapping("/file/upload")
    ResponseBase upload(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "id") String id, String action);
}
