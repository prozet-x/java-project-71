package hexlet.code;

import hexlet.code.formatters.Formatters;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.ArrayList;

public class Differ {
    public static String generate(
            @NotNull Map<String, ?> data1,
            @NotNull Map<String, ?> data2,
            String formatterName) throws Exception {
        Set<String> keys = new HashSet<>();
        keys.addAll(data1.keySet());
        keys.addAll(data2.keySet());

        var sortedKeys = keys.stream().sorted().toList();

        List<Map<String, ?>> diff = new ArrayList<>();
        sortedKeys.forEach(key -> {
            Map<String, ?> newRecord;
            if (!data1.containsKey(key)) {
                newRecord = Map.of("status", "add", "key", key, "value", getNullAsString(data2.get(key)));
            } else if (!data2.containsKey(key)) {
                newRecord = Map.of("status", "del", "key", key, "value", getNullAsString(data1.get(key)));
            } else {
                Object value1 = data1.get(key);
                Object value2 = data2.get(key);
                if ((value1 == null && value2 == null)
                        || (value1 != null && value1.equals(value2))) {
                    newRecord = Map.of("status", "const", "key", key, "value", getNullAsString(value1));
                } else {
                    newRecord = Map.of(
                            "status", "change",
                            "key", key,
                            "value", getNullAsString(value2),
                            "oldValue", getNullAsString(value1));
                }
            }
            diff.add(newRecord);
        });

        return Formatters.getFormatter(formatterName).format(diff);
    }

    private static Object getNullAsString(Object value) {
        return value == null ? "null" : value;
    }
}
