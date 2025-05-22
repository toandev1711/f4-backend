package com.example.f4backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String getImageUrlAfterUpload(MultipartFile file) throws IOException {
        Map upload = cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "Image"
                ));
        return (String) upload.get("url");
    }

    public String updateImage(MultipartFile newFile, String oldImageUrl) throws IOException {
        String publicId = extractPublicIdFromUrl(oldImageUrl);
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        Map upload = cloudinary.uploader().upload(newFile.getBytes(),
                ObjectUtils.asMap(
                        "folder", "Image"
                ));
        return (String) upload.get("url");
    }

    //TODO : delete image with imageURL

    //get publicId
    private String extractPublicIdFromUrl(String imageUrl) {
        int uploadIndex = imageUrl.indexOf("/upload/");
        if (uploadIndex == -1) {
            throw new IllegalArgumentException("Invalid Cloudinary URL: " + imageUrl);
        }
        String afterUpload = imageUrl.substring(uploadIndex + "/upload/".length());

        if (afterUpload.startsWith("v")) {
            int slashIndex = afterUpload.indexOf('/');
            if (slashIndex != -1) {
                afterUpload = afterUpload.substring(slashIndex + 1);
            }
        }
        int dotIndex = afterUpload.lastIndexOf('.');
        if (dotIndex != -1) {
            afterUpload = afterUpload.substring(0, dotIndex);
        }
        return afterUpload;  // ví dụ: Image/spqxfi2elt94itv7n3g9
    }
}
