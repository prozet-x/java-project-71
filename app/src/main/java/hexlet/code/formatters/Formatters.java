package hexlet.code.formatters;

import java.util.Map;

public class Formatters {
    private static Map<String, Formatter> map = Map.of(
            "stylish", new Stylish(),
            "plain", new Plain(),
            "json", new Json()
            );
    public static Formatter getFormatter(String formatterName) throws Exception {
        if (map.containsKey(formatterName)) {
            return map.get(formatterName);
        }
        throw new Exception("Wrong formatter name. Only 'stylish', 'plain' or 'json' are available");
    }
}
