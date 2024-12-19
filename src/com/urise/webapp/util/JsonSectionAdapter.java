package com.urise.webapp.util;
//
//import com.google.gson.*;
//import com.urise.webapp.model.ListSection;
//import com.urise.webapp.model.OrganizationSection;
//import com.urise.webapp.model.Section;
//import com.urise.webapp.model.TextSection;
//
//import java.lang.reflect.Type;
//
//public class JsonSectionAdapter implements JsonDeserializer<Section>, JsonSerializer<Section> {
//    private static final String TYPE = "type";
//    private static final String DATA = "data";
//
//    @Override
//    public JsonElement serialize(Section section, Type typeOfSrc, JsonSerializationContext context) {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty(TYPE, section.getClass().getSimpleName());
//        jsonObject.add(DATA, context.serialize(section));
//        return jsonObject;
//    }
//
//    @Override
//    public Section deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        JsonObject jsonObject = json.getAsJsonObject();
//        String type = jsonObject.get(TYPE).getAsString();
//        JsonElement data = jsonObject.get(DATA);
//
//        try {
//            switch (type) {
//                case "TextSection":
//                    return context.deserialize(data, TextSection.class);
//                case "ListSection":
//                    return context.deserialize(data, ListSection.class);
//                case "OrganizationSection":
//                    return context.deserialize(data, OrganizationSection.class);
//                default:
//                    throw new JsonParseException("Unknown section type: " + type);
//            }
//        } catch (Exception e) {
//            throw new JsonParseException(e);
//        }
//    }
//}