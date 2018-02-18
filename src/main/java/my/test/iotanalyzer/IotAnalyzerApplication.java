package my.test.iotanalyzer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class IotAnalyzerApplication {

    //In simulate mode there is no context, therefore these constants are instead of config.properties
    private static final String SIMULATION_KEY = "--simulate";
    private static final String URL = "http://localhost:8080/store/";
    private static final Map<String, String> HEADERS = new HashMap<>();
    private static final HttpHeaders headers = new HttpHeaders();
    static {
        headers.set("X-Authority-Hash", "4028843b619e9c5b01619e9c5be80000");
    }
    private static final HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

    public static void main(String[] args) {
        if (Arrays.stream(args).filter(SIMULATION_KEY::equalsIgnoreCase).count() > 0) {
            simulate();
        } else {
            SpringApplication.run(IotAnalyzerApplication.class, args);
        }
    }

    private static void simulate() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(getRunnable(executorService, "Metric_1_", 100));
        executorService.execute(getRunnable(executorService, "Metric_2_", 1000));
        executorService.execute(getRunnable(executorService, "Metric_3_", 10000));
    }

    private static Runnable getRunnable(ExecutorService executorService, String name, int multiplier) {
        return () -> {
            RestTemplate restTemplate = new RestTemplate();
            while (!executorService.isShutdown() && !executorService.isTerminated()) {
                String localUrl = URL + name + "/" + Math.random() * multiplier;
                restTemplate.postForEntity(localUrl, entity, String.class, HEADERS);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    executorService.shutdown();
                }
            }
        };
    }
}
