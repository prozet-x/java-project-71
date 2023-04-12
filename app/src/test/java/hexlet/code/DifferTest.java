package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

class DifferTest {
    @Test
    public void testJSON() {
        var fix1Path = "src/test/resources/fix1.json";
        var fix2Path = "src/test/resources/fix2.json";
        var fix1fix2diffPath = "src/test/resources/f1f2res.txt";
        var fix2fix1diffPath = "src/test/resources/f2f1res.txt";
        var fix1fix1diffPath = "src/test/resources/f1f1res.txt";

        String file1Data;
        String file2Data;
        String f1f2diffData;
        String f2f1diffData;
        String f1f1diffData;
        try {
            file1Data = Files.readString(Path.of(fix1Path));
            file2Data = Files.readString(Path.of(fix2Path));
            f1f2diffData = Files.readString(Path.of(fix1fix2diffPath));
            f2f1diffData = Files.readString(Path.of(fix2fix1diffPath));
            f1f1diffData = Files.readString(Path.of(fix1fix1diffPath));
        } catch (IOException ex) {
            System.out.println("File " + ex.getMessage() + " does not exist.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, ?> json1;
        Map<String, ?> json2;
        try {
            json1 = mapper.readValue(file1Data, new TypeReference<Map<String, ?>>() { });
            json2 = mapper.readValue(file2Data, new TypeReference<Map<String, ?>>() { });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        assertEquals(f1f2diffData, Differ.generate(json1, json2));
        assertEquals(f2f1diffData, Differ.generate(json2, json1));
        assertEquals(f1f1diffData, Differ.generate(json1, json1));
    }

    @Test
    public void testYAML() {
        var fix1Path = "src/test/resources/fix1.yml";
        var fix2Path = "src/test/resources/fix2.yml";
        var fix1fix2diffPath = "src/test/resources/f1f2res.txt";
        var fix2fix1diffPath = "src/test/resources/f2f1res.txt";
        var fix1fix1diffPath = "src/test/resources/f1f1res.txt";

        String file1Data;
        String file2Data;
        String f1f2diffData;
        String f2f1diffData;
        String f1f1diffData;
        try {
            file1Data = Files.readString(Path.of(fix1Path));
            file2Data = Files.readString(Path.of(fix2Path));
            f1f2diffData = Files.readString(Path.of(fix1fix2diffPath));
            f2f1diffData = Files.readString(Path.of(fix2fix1diffPath));
            f1f1diffData = Files.readString(Path.of(fix1fix1diffPath));
        } catch (IOException ex) {
            System.out.println("File " + ex.getMessage() + " does not exist.");
            return;
        }

        ObjectMapper mapper = new YAMLMapper();
        Map<String, ?> yaml1;
        Map<String, ?> yaml2;
        try {
            yaml1 = mapper.readValue(file1Data, new TypeReference<>() { });
            yaml2 = mapper.readValue(file2Data, new TypeReference<>() { });
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        assertEquals(f1f2diffData, Differ.generate(yaml1, yaml2));
        assertEquals(f2f1diffData, Differ.generate(yaml2, yaml1));
        assertEquals(f1f1diffData, Differ.generate(yaml1, yaml1));
    }
}
