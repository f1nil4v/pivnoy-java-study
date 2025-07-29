package ttv.poltoraha.pivka.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

// Как правило все имеющиеся метрики создаются в отдельном классе.
@Component
public class CustomMetrics {
    private final MeterRegistry registry;
    private final ConcurrentHashMap<String, Counter> counters = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Timer> timers = new ConcurrentHashMap<>();

    public CustomMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    public void increment(String name, String... tags) {
        String key = name + String.join("-", tags);
        counters.computeIfAbsent(key, k -> Counter.builder(name).tags(tags).register(registry)).increment();
    }

    public Timer.Sample startTimer() {
        return Timer.start(registry);
    }

    public void stopTimer(Timer.Sample sample, String name, String... tags) {
        String key = name + String.join("-", tags);
        Timer timer = timers.computeIfAbsent(key, k -> Timer.builder(name).tags(tags).register(registry));
        sample.stop(timer);
    }

}
