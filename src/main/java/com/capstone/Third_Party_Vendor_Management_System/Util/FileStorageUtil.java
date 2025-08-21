package com.capstone.Third_Party_Vendor_Management_System.Util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileStorageUtil {
    private static final String BASE_DIR = "C:\\Users\\aswathy.k\\IdeaProjects\\Third-Party-Vendor-Management-System\\storing_data";
    public static String saveFile(MultipartFile file, String vendorId, String documentName) throws IOException {
        String dirPath =BASE_DIR + vendorId + "/";
        Files.createDirectories(Paths.get(dirPath));
        String filePath =dirPath + documentName + "_" + file.getOriginalFilename();
        Path path =Paths.get(filePath);
        Files.write(path, file.getBytes());
        return filePath;
    }
    public static boolean deleteFile(String filePath) throws IOException{
        return Files.deleteIfExists(Paths.get(filePath));
    }
}
