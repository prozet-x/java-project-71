package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Plain implements Formatter {
    public String format(List<Map<String, ?>> data) {
        List<String> rows = new ArrayList<>();
        data.forEach(str -> {
            Object status = str.get("status");
            if (status.equals("add")) {
                rows.add(String.format(
                        "Property '%s' was added with value: %s", str.get("key"),
                        getFormattedValue(str.get("value"))
                ));
            } else if (status.equals("del")) {
                rows.add(String.format("Property '%s' was removed", str.get("key")));
            } else if (status.equals("change")) {
                rows.add(String.format(
                        "Property '%s' was updated. From %s to %s",
                        str.get("key"), getFormattedValue(str.get("oldValue")),
                        getFormattedValue(str.get("value"))
                ));
            }
        });
        return String.join("\n", rows);
    }

    private static String getFormattedValue(Object value) {
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        } else if (value.equals("null")) {
            return value.toString();
        } else if (value instanceof String) {
            return String.format("'%s'", value.toString());
        }
        return  value.toString();
    }
}
