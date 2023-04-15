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
        var fix1Path = Path.of("src/test/resources/fix1.json");
        var fix2Path = Path.of("src/test/resources/fix2.json");
        differTest(fix1Path, fix2Path, "stylish");
    }

    @Test
    public void testYAMLStylish() throws Exception {
        var fix1Path = Path.of("src/test/resources/fix1.yml");
        var fix2Path = Path.of("src/test/resources/fix2.yml");
        differTest(fix1Path, fix2Path, "stylish");
    }

    @Test
    public void testJsonYamlPlain() throws Exception {
        var fix1Path = Path.of("src/test/resources/fix1.json");
        var fix2Path = Path.of("src/test/resources/fix2.yml");
        differTest(fix1Path, fix2Path, "plain");
    }

    private void differTest(Path path1, Path path2, String formatName) throws Exception {
        final var f1f2ResPath = Path.of(String.format("src/test/resources/f1f2%shRes.txt", formatName));
        final var f2f1ResPath = Path.of(String.format("src/test/resources/f2f1%shRes.txt", formatName));
        final var f1f1ResPath = Path.of(String.format("src/test/resources/f1f1%shRes.txt", formatName));

        try {
            data1 = Parser.parseFile(path1);
            data2 = Parser.parseFile(path2);
            f1f2Res = Files.readString(f1f2ResPath);
            f2f1Res = Files.readString(f2f1ResPath);
            f1f1Res = Files.readString(f1f1ResPath);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }

        String diff;

        diff = Differ.generate(data1, data2, formatName);
        assertEquals(f1f2Res, diff);

        diff = Differ.generate(data2, data1, formatName);
        assertEquals(f2f1Res, diff);

        diff = Differ.generate(data1, data1, formatName);
        assertEquals(f1f1Res, diff);
    }
}
