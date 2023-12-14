package com.Pubrunda;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;

public class DTOMapper {

    private static final ModelMapper modelMapper = new ModelMapper();


    public static <T, C> C convertToDto(T object, Class<C> clazz) {
        return modelMapper.map(object, clazz);
    }

    public static <T, C> List<C> convertToDto(Collection<T> collection, Class<C> dtoClass) {
        return collection.stream().map(object -> convertToDto(object, dtoClass)).toList();
    }

}