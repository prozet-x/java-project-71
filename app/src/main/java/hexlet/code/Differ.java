package hexlet.code;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;

public class Differ {
    public static @NotNull String generate(@NotNull Map<String, ?> data1, @NotNull Map<String, ?> data2) {
        Set<String> keys = new HashSet<>();
        keys.addAll(data1.keySet());
        keys.addAll(data2.keySet());
        var sortedKeys = keys.stream().sorted().toList();
        List<String> diff = new ArrayList<>();
        diff.add("{");
        sortedKeys.forEach(key -> {
            if (!data1.containsKey(key)) {
                diff.add(String.format("  + %s: %s", key, data2.get(key) == null ? "null" : data2.get(key)));
            } else if (!data2.containsKey(key)) {
                diff.add(String.format("  - %s: %s", key, data1.get(key) == null ? "null" : data1.get(key)));
            } else if (data1.get(key) == null || data2.get(key) == null) {
                if (data1.get(key) == null && data2.get(key) == null) {
                    diff.add(String.format("    %s: %s", key, "null" ));
                } else {
                    diff.add(String.format("  - %s: %s", key, data1.get(key) == null ? "null" : data1.get(key)));
                    diff.add(String.format("  + %s: %s", key, data2.get(key) == null ? "null" : data2.get(key)));
                }
            } else if (data1.get(key).equals(data2.get(key))) {
                diff.add(String.format("    %s: %s", key, data1.get(key) == null ? "null" : data1.get(key)));
            } else {
                diff.add(String.format("  - %s: %s", key, data1.get(key) == null ? "null" : data1.get(key)));
                diff.add(String.format("  + %s: %s", key, data2.get(key) == null ? "null" : data2.get(key)));
            }
        });
        diff.add("}");
        return String.join("\n", diff);
    }
}
