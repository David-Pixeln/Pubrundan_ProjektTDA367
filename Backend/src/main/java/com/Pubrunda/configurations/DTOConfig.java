package com.Pubrunda.configurations;

import com.Pubrunda.DTOMapper;
import com.Pubrunda.entities.image.Image;
import com.Pubrunda.entities.image.ImageService;
import com.Pubrunda.entities.image.dto.response.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class DTOConfig {

    private final ImageService imageService;

    @Bean
    public void imageDTOConfig() {
        if (DTOMapper.getTypeMap(Image.class, ImageDTO.class) != null) {
            return;
        }

        TypeMap<Image, ImageDTO> propertyMapper = DTOMapper.createTypeMap(Image.class, ImageDTO.class);

        Converter<String, byte[]> imageConverter = ctx -> {
            try {
                return imageService.getImageByPath(ctx.getSource());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        propertyMapper.addMappings(mapping -> mapping.using(imageConverter).map(Image::getPath, ImageDTO::setData));
    }

}
