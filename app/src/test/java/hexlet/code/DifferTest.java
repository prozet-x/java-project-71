package hexlet.code;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

class DifferTest {
    @Test
    public void testS() throws Exception {
        var fix1Path = "../../resourses/fix1.json";
        var fix2Path = "../../resourses/fix2.json";
        String file1Data;
        String file2Data;
        try {
            file1Data = Files.readString(Path.of(fix1Path));
            file2Data = Files.readString(Path.of(fix2Path));
        } catch (IOException ex) {
            System.out.println("File " + ex.getMessage() + " does not exist.");
            return;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, ?> json1 = mapper.readValue(file1Data, new TypeReference<Map<String, ?>>() { });
        Map<String, ?> json2 = mapper.readValue(file2Data, new TypeReference<Map<String, ?>>() { });
        System.out.println(Differ.generate(json1, json2));

    }
}
