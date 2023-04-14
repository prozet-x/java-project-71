package hexlet.code;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;

public class Differ {
    public static List<Map<String, String>> generate(@NotNull Map<String, ?> data1, @NotNull Map<String, ?> data2) {
        Set<String> keys = new HashSet<>();
        keys.addAll(data1.keySet());
        keys.addAll(data2.keySet());
        var sortedKeys = keys.stream().sorted().toList();
        List<Map<String, String>> diff = new ArrayList<>();
        sortedKeys.forEach(key -> {
            Map<String, String> newRecord;
            if (!data1.containsKey(key)) {
                newRecord = Map.of("status", "add", "key", key, "value", getAsString(data2.get(key)));
            } else if (!data2.containsKey(key)) {
                newRecord = Map.of("status", "del", "key", key, "value", getAsString(data1.get(key)));
            } else if (data1.get(key) == null || data2.get(key) == null) {
                if (data1.get(key) == null && data2.get(key) == null) {
                    newRecord = Map.of("status", "const", "key", key, "value", "null");
                } else {
                    newRecord = Map.of(
                            "status", "change",
                            "key", key,
                            "value", getAsString(data2.get(key)),
                            "oldValue", getAsString(data1.get(key)));
                }
            } else if (data1.get(key).equals(data2.get(key))) {
                newRecord = Map.of("status", "const", "key", key, "value", getAsString(data1.get(key)));
            } else {
                newRecord = Map.of("status", "change",
                        "key", key,
                        "value", getAsString(data2.get(key)),
                        "oldValue", getAsString(data1.get(key)));
            }
            diff.add(newRecord);
        });
        return diff;
    }

    private static String getAsString(Object value) {
        return value == null ? "null" : value.toString();
    }
}
