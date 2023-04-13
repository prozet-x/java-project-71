package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

class DifferTest {
    private static String f1f2diffData;
    private static String f2f1diffData;
    private static String f1f1diffData;
    private static Map<String, ?> data1;
    private static Map<String, ?> data2;

    @BeforeAll
    public static void beforeAll() {
        final var fix1fix2diffPath = Path.of("src/test/resources/f1f2res.txt");
        final var fix2fix1diffPath = Path.of("src/test/resources/f2f1res.txt");
        final var fix1fix1diffPath = Path.of("src/test/resources/f1f1res.txt");

        try {
            f1f2diffData = Files.readString(fix1fix2diffPath);
            f2f1diffData = Files.readString(fix2fix1diffPath);
            f1f1diffData = Files.readString(fix1fix1diffPath);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Test
    public void testJSON() {
        var fix1Path = Path.of("src/test/resources/fix1.json");
        var fix2Path = Path.of("src/test/resources/fix2.json");
        differTest(fix1Path, fix2Path);
    }

    @Test
    public void testYAML() {
        var fix1Path = Path.of("src/test/resources/fix1.yml");
        var fix2Path = Path.of("src/test/resources/fix2.yml");
        differTest(fix1Path, fix2Path);
    }

    private void differTest(Path path1, Path path2) {
        try {
            data1 = Parser.parseFile(path1);
            data2 = Parser.parseFile(path2);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }

        assertEquals(f1f2diffData, Differ.generate(data1, data2));
        assertEquals(f2f1diffData, Differ.generate(data2, data1));
        assertEquals(f1f1diffData, Differ.generate(data1, data1));
    }
}
