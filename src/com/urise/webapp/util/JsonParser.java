package com.urise.webapp.util;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.urise.webapp.model.Section;


//import java.io.Reader;
//import java.io.Writer;
//import java.time.LocalDate;
//
//public class JsonParser {
//    private static Gson GSON = new GsonBuilder()
//            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
////            .registerTypeAdapter(Section.class, new JsonSectionAdapter())
//
//
//    public static <T> T read(Reader reader, Class<T> clazz) {
//        return GSON.fromJson(reader, clazz);
//    }
//
//    public static <T> void write(T object, Writer writer) {
//        GSON.toJson(object, writer);
//    }
//
//}