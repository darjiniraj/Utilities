package csv;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

/**
 * created by pc on Apr, 2019
 */
public class CSVParseFromLog {

    public static void main(String[] args) throws Exception {
        final Path path = Paths.get("C:\\Users\\pc\\Desktop\\");
        final Path txt = path.resolve("all-log.log");
        final Path csv = path.resolve("log.csv");
        final Path deleteLine = path.resolve("delete.txt");
        Set<String> deleteLineSet;

        String header = "TRANSACTION_ID , NAME , EXECUTION_TIME , TYPE , START_DATE , END_DATE";

        System.out.println("File Processing Started..");

        try (Stream<String> stream = lines(deleteLine)) {
            deleteLineSet = stream.collect(Collectors.toSet());
        }

        List<String> allContent = Files.readAllLines(txt);
        try (final PrintWriter pw = new PrintWriter(Files.newBufferedWriter(csv, StandardOpenOption.CREATE_NEW))) {
            pw.print(header);
            pw.println();
            for (String deleteWord : deleteLineSet) {
                allContent = allContent.stream()
                        .map(line -> line.replaceAll(deleteWord, ""))
                        .map(line -> line.split("\\|"))
                        .map(line -> String.join(",", line))
                        .collect(Collectors.toList());
            }
            allContent.forEach(pw::println);
            System.out.println("File Processing Completed..");
        }
    }
}
