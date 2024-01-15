//package com.epam.esm.config.app;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//
//public class ObjectMapperSingleton {
//    private static final ObjectMapper objectMapper;
//
//    static {
//        objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//    }
//
//    private ObjectMapperSingleton() {
//    }
//
//    public static ObjectMapper getInstance() {
//        return objectMapper;
//    }
//}
