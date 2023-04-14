package hexlet.code.formatters;

import java.util.Map;

public class Formatters {
    private static Map<String, Formatter> map = Map.of(
            "stylish", new Stylish()
            );
    public static Formatter getFormatter(String formatterName) {
        return map.get(formatterName);
    }
}
