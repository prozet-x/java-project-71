package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(
        name = "gendiff",
        version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.",
        mixinStandardHelpOptions = true)
public class App implements Callable<Integer> {
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    Path filePath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    Path filePath2;

    @Option(
            names = {"-f", "--format"},
            paramLabel = "format",
            description = "output format [default: ${DEFAULT-VALUE}]",
            defaultValue = "stylish")
    String format;

    @Override
    //@SuppressWarnings("unchecked")
    public Integer call() throws Exception {
        /*String file1Data;
        String file2Data;
        try {
            file1Data = Files.readString(filePath1);
            file2Data = Files.readString(filePath2);
        } catch (IOException ex) {
            System.out.println("File " + ex.getMessage() + " does not exist.");
            return 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return 1;
        }*/

        //ObjectMapper mapper = new ObjectMapper();
        Map<String, ?> data1 = Parser.parseFile(filePath1);
        Map<String, ?> data2 = Parser.parseFile(filePath2);

        System.out.println(Differ.generate(data1, data2));
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
