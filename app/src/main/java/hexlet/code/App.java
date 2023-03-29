package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff", version = "gendiff 1.0", description = "Compares two configuration files and shows a difference.", mixinStandardHelpOptions = true)
public class App implements Callable<Integer> {
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    String filePath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    String filePath2;

    @Option(names = {"-f", "--format"}, paramLabel = "format", description = "output format [default: ${DEFAULT-VALUE}]", defaultValue = "stylish")
    String format;

    @Override
    public Integer call() {
        System.out.println("Hello, world!");
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
