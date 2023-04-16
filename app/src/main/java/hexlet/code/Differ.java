package hexlet.code;

import hexlet.code.formatters.Formatters;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Differ {
    public static String generate(
            @NotNull Map<String, ?> data1,
            @NotNull Map<String, ?> data2,
            String formatterName) throws Exception {

        Set<String> keys = new HashSet<>();
        keys.addAll(data1.keySet());
        keys.addAll(data2.keySet());

        List<Map<String, ?>> diff = new ArrayList<>();

        var sortedKeys = keys.stream().sorted().toList();
        Map<String, String> as = new HashMap<>();
        sortedKeys.forEach(key -> {
            Object v1  = data1.get(key);
            Object v2  = data2.get(key);
            Map<String, ?> newRecord;

            if (!data1.containsKey(key)) {
                newRecord = getNewDiffRecord("add", key, v2);
            } else if (!data2.containsKey(key)) {
                newRecord = getNewDiffRecord("del", key, v1);
            } else if ((v1 == null && v2 == null) || (v1 != null && v1.equals(v2))) {
                newRecord = getNewDiffRecord("const", key, v1);
            } else {
                newRecord = getNewDiffRecord("change", key, v2, v1);
            }

            diff.add(newRecord);
        });

        return Formatters.getFormatter(formatterName).format(diff);
    }

    private static Map<String, ?> getNewDiffRecord(String status, Object key, Object value) {
        Map<String, Object> record = new HashMap<>();
        record.put("key", key);
        record.put("value", value);
        record.put("status", status);
        return record;
    }

    private static Map<String, ?> getNewDiffRecord(String status, Object key, Object newValue, Object oldValue) {
        Map<String, Object> record = new HashMap<>();
        record.put("key", key);
        record.put("value", newValue);
        record.put("oldValue", oldValue);
        record.put("status", status);
        return record;
    }
}
