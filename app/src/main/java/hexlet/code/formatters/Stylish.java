package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Stylish implements Formatter {
    public String format(List<Map<String, ?>> data) {
        List<String> rows = new ArrayList<>();
        rows.add("{");
        data.forEach(str -> {
            if (str.get("status").equals("add")) {
                rows.add(String.format("  + %s: %s", str.get("key"), getAsString(str.get("value"))));
            } else if (str.get("status").equals("del")) {
                rows.add(String.format("  - %s: %s", str.get("key"), getAsString(str.get("value"))));
            } else if (str.get("status").equals("const")) {
                rows.add(String.format("    %s: %s", str.get("key"), getAsString(str.get("value"))));
            } else {
                rows.add(String.format("  - %s: %s", str.get("key"), getAsString(str.get("oldValue"))));
                rows.add(String.format("  + %s: %s", str.get("key"), getAsString(str.get("value"))));
            }
        });
        rows.add("}");
        return String.join("\n", rows);
    }

    private static String getAsString(Object value) {
        return String.valueOf(value);
    }
}
