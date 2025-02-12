import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class ThreadPoolTest {

    public static void main(String[] args) {
        final List<Future<String>> futureTasks = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try  {
            IntStream.range(0, 10).forEach(i -> {
                Future<String> futureTask = executorService.submit(() -> (i + 1) + " > Hello World !");
                futureTasks.add(futureTask);
            });

            futureTasks.forEach(futureTask -> {
                try {
                    System.out.println(futureTask.get()); 
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        } finally {
            executorService.shutdown();
        }

    }
}
