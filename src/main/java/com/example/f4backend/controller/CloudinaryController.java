package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.service.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ApiResponse<String> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        String uploadedImage = cloudinaryService.getImageUrlAfterUpload(image);

        return ApiResponse.<String>builder()
                .code(ErrorCode.CREATE_IDENTIFIERCARD_SUCCESS.getCode())
                .result(uploadedImage)
                .message("Upload successfull")
                .build();
    }

    @PostMapping("/update")
    public ApiResponse<String> updateImage(
            @RequestParam("newImage") MultipartFile newImage,
            @RequestParam("oldUrl") String oldUrl
    ) throws IOException {
        String newUrl = cloudinaryService.updateImage(newImage, oldUrl);
        return ApiResponse.<String>builder()
                .code(ErrorCode.CREATE_IDENTIFIERCARD_SUCCESS.getCode())
                .message("Update successful")
                .result(newUrl)
                .build();
    }
}
