package com.Pubrunda.entities.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private static final String IMAGE_ROOT = "src/main/resources/static/images/";

    private final ImageRepository imageRepository;


    public Image upload(MultipartFile file) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String uniqueFilename = UUID.randomUUID() + "_" + LocalDateTime.now().format(formatter) + file.getOriginalFilename();
        String imagePath = IMAGE_ROOT + uniqueFilename;

        Image image = imageRepository.save(
                Image.builder()
                        .name(Objects.requireNonNull(file.getOriginalFilename()))
                        .type(Objects.requireNonNull(file.getContentType()))
                        .path(imagePath)
                        .build()
        );

        file.transferTo(new File(imagePath).toPath());

        return image;
    }

    public List<Image> upload(List<MultipartFile> files) throws IOException {
        return files.stream().map(file -> {
            try {
                return upload(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    public byte[] getImageById(long id) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow();
        return getImageByPath(image.getPath());
    }

    public byte[] getImageByPath(String path) throws IOException {
        return Files.readAllBytes(new File(path).toPath());
    }

}
