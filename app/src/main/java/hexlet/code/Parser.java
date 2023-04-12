package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Parser {
    public static Map<String, ?> parseFile(Path filePath) throws Exception {
        String fileExtension = getFileExtension(filePath);

        ObjectMapper mapper;
        if (fileExtension.equals("json")) {
            mapper = new ObjectMapper();
        } else if (fileExtension.equals("yaml") || fileExtension.equals("yml")) {
            mapper = new YAMLMapper();
        } else {
            throw new Exception("bad file extension. Need yaml, yml or json. " + fileExtension + " given");
        }

        String fileData = Files.readString(filePath);
        Map<String, ?> data = mapper.readValue(fileData, new TypeReference<Map<String, ?>>() { });

        return data;
    }

    private static String getFileExtension(Path filePath) {
        String fullName = filePath.toString().toLowerCase();
        String[] partsOfFilePath = fullName.split("\\.");
        return partsOfFilePath[partsOfFilePath.length - 1];
    }
}
