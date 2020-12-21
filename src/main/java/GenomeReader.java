import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GenomeReader {

    private final String path;

    public GenomeReader(String path) {
        this.path = path;
    }

    public List<String> take(int countOfPortions) {
        List<String> parts = new LinkedList<>();
        Queue<Character> queue = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (queue.size() == countOfPortions) {
                        StringBuilder sb = new StringBuilder();
                        for (Character character : queue) {
                            sb.append(character);
                        }
                        parts.add(sb.toString());
                        queue.poll();
                    }
                    queue.add(line.charAt(i));
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return parts;
    }
}
