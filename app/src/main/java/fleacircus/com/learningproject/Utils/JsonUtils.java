package fleacircus.com.learningproject.Utils;

import com.google.gson.Gson;

public class JsonUtils {
    public static String objectToJsonString(Object object) {
        return new Gson().toJson(object);
    }

    public static Object jsonStringToObject(String string, Class name) {
        return new Gson().fromJson(string, name);
    }
}
