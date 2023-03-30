package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Command(name = "gendiff", version = "gendiff 1.0", description = "Compares two configuration files and shows a difference.", mixinStandardHelpOptions = true)
public class App implements Callable<Integer> {
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    Path filePath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    Path filePath2;

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: ${DEFAULT-VALUE}]", defaultValue = "stylish")
    String format;

    @Override
    public Integer call() throws Exception {
        String file1Data = Files.readString(filePath1);
        String file2Data = Files.readString(filePath2);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, ?> json1 = mapper.readValue(file1Data, Map.class);
        Map<String, ?> json2 = mapper.readValue(file2Data, Map.class);
        Set<String> keys = new HashSet<>();
        keys.addAll(json1.keySet());
        keys.addAll(json2.keySet());
        List<String> sortedKeys = keys.stream().sorted().collect(Collectors.toList());
        List<String> diff = new ArrayList<>();
        diff.add("{");
        sortedKeys.forEach(key -> {
            if (!json1.containsKey(key)) {
                diff.add(String.format("  + %s: %s", key, json2.get(key)));
            } else if (!json2.containsKey(key)) {
                diff.add(String.format("  - %s: %s", key, json1.get(key)));
            } else if (json1.get(key).equals(json2.get(key))) {
                diff.add(String.format("    %s: %s", key, json1.get(key)));
            } else {
                diff.add(String.format("  - %s: %s", key, json1.get(key)));
                diff.add(String.format("  + %s: %s", key, json2.get(key)));
            }
        });
        diff.add("}");
        System.out.println(diff.stream().collect(Collectors.joining("\n")));
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
