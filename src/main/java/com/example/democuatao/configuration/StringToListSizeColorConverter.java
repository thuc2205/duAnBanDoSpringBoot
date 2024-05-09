package com.example.democuatao.configuration;

import com.example.democuatao.dtos.listSizeColor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;import org.springframework.core.convert.converter.Converter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class StringToListSizeColorConverter implements Converter<String, List<listSizeColor>> {
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public List<listSizeColor> convert(String source) {
        List<listSizeColor> listSizeColors = new ArrayList<>();
        try {
            listSizeColors = objectMapper.readValue(source, objectMapper.getTypeFactory().constructCollectionType(List.class, listSizeColor.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return listSizeColors;
    }
}
