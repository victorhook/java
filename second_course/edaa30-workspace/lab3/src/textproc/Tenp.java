package textproc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Tenp {

    public static void main(String[] args) throws IOException {

        List<File> files = Files.walk(Paths.get(""))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());



    }

}
