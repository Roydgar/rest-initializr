package tk.roydgar.restinitializr.util;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

@UtilityClass
public class FileExtenderUtils {

    public File readAndAddDependency(String fileName, String splitKeyword, String content) throws IOException {
        String directory = fileName.substring(0, fileName.indexOf(File.separator));
        File file = new File(directory + File.separator + "result.xml");
        try (FileWriter fileWriter = new FileWriter(file);
             Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(line -> {
                try {
                    fileWriter.append(line).append(System.lineSeparator());

                    if (line.contains(splitKeyword)) {
                        String[] contentLines = content.split("\n");
                        Arrays.stream(contentLines)
                                .map(contentLine -> "\t\t" + contentLine)
                                .forEach(contentLine -> {
                                    try {
                                        fileWriter.append(contentLine).append(System.lineSeparator());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });

                        fileWriter.append(System.lineSeparator());
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        return file;
    }
}
