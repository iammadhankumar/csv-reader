import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CsvProcessor {
    public static void main(String[] args) {
        Path path = Paths.get("data.csv");
        try(Stream<String> lines = Files.lines(path);) {

            // need to use flatmap bcoz we are returning Stream

            lines.filter(line -> !line.startsWith("#"))
                    .flatMap(line -> lineToPerson(line))
                    .forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Stream<Person> lineToPerson(String line) {
        String[] personAttributes = line.split(";");
        // exception due to some buggy lines in between in csv file
        // which doesn't have 1st and 2nd array index after splitting
        // so when exception return empty Stream
        try {
            return Stream.of(new Person(personAttributes[0], Integer.parseInt(personAttributes[1]), personAttributes[2]));
        } catch(Exception e) {
            return Stream.empty();
        }
    }
}
