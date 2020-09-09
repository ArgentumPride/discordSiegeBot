package ua.pride.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

@Component
public class JsonUtils {

    public static JsonObject convertStringToJsonObject(String jsonString) {
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public static String getStringFromJsonObject(JsonObject jsonObject, String property) {
        return jsonObject.get(property).getAsString();
    }

    public static Long getLongFromJsonObject(JsonObject jsonObject, String property) {
        return jsonObject.get(property).getAsLong();
    }

    public static Double getDoubleFromJsonObject(JsonObject jsonObject, String property) {
        return jsonObject.get(property).getAsDouble();
    }
}
