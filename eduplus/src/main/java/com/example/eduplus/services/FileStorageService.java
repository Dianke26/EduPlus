package com.example.eduplus.services;

import com.example.eduplus.exception.InvalidFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final List<String> ALLOWED_IMAGE_TYPES =
            List.of("image/jpeg", "image/png", "image/jpg");

    private static final List<String> ALLOWED_DOC_TYPES =
            List.of("application/pdf");

    public String storePhoto(MultipartFile file) {
        validateFile(file, ALLOWED_IMAGE_TYPES, "Seuls les formats JPEG et PNG sont autorisés pour la photo");
        return saveFile(file, "photos");
    }

    public String storeDocument(MultipartFile file) {
        validateFile(file, ALLOWED_DOC_TYPES, "Seul le format PDF est autorisé pour le document");
        return saveFile(file, "docs");
    }

    private void validateFile(MultipartFile file, List<String> allowedTypes, String errorMessage) {
        if (file == null || file.isEmpty()) {
            throw new InvalidFileException("Le fichier est vide ou manquant");
        }

        String contentType = file.getContentType();
        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new InvalidFileException(errorMessage);
        }
    }

    private String saveFile(MultipartFile file, String subFolder) {
        try {
            Path uploadPath = Paths.get(uploadDir, subFolder);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";

            String uniqueFilename = UUID.randomUUID() + extension;
            Path targetPath = uploadPath.resolve(uniqueFilename);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return "/" + subFolder + "/" + uniqueFilename;

        } catch (IOException e) {
            throw new InvalidFileException("Erreur lors de l'enregistrement du fichier : " + e.getMessage());
        }
    }
}