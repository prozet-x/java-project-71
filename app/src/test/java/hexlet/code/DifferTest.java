package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

class DifferTest {
    private static Map<String, ?> data1;
    private static Map<String, ?> data2;
    private static String f1f2Res;
    private static String f2f1Res;
    private static String f1f1Res;

    @Test
    public void testJSONStylish() throws Exception {
        differTest("src/test/resources/fix1.json", "src/test/resources/fix2.json", "stylish");
    }

    @Test
    public void testYAMLStylish() throws Exception {
        differTest("src/test/resources/fix1.yml", "src/test/resources/fix2.yml", "stylish");
    }

    @Test
    public void testJsonYamlPlain() throws Exception {
        differTest("src/test/resources/fix1.json", "src/test/resources/fix2.yml", "plain");
    }

    @Test
    public void testJsonYamlJson() throws Exception {
        differTest("src/test/resources/fix1.json", "src/test/resources/fix2.yml", "json");
    }

    private void differTest(String filePath1, String filePath2, String formatName) throws Exception {
        final var f1f2ResPath = Path.of(String.format("src/test/resources/f1f2%sRes.txt", formatName));
        final var f2f1ResPath = Path.of(String.format("src/test/resources/f2f1%sRes.txt", formatName));
        final var f1f1ResPath = Path.of(String.format("src/test/resources/f1f1%sRes.txt", formatName));

        f1f2Res = Files.readString(f1f2ResPath);
        f2f1Res = Files.readString(f2f1ResPath);
        f1f1Res = Files.readString(f1f1ResPath);

        String diff;

        diff = Differ.generate(filePath1, filePath2, formatName);
        assertEquals(f1f2Res, diff);

        diff = Differ.generate(filePath2, filePath1, formatName);
        assertEquals(f2f1Res, diff);

        diff = Differ.generate(filePath1, filePath1, formatName);
        assertEquals(f1f1Res, diff);
    }
}
