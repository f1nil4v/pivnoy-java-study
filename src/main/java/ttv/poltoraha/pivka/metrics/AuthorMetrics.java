package ttv.poltoraha.pivka.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;


@Component
public class AuthorMetrics {
    private final MeterRegistry registry;
    private final ConcurrentHashMap<String, Counter> counters = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Timer> timers = new ConcurrentHashMap<>();

    public AuthorMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    public void incrementList() {
        counters.computeIfAbsent("api.author.list.count",
                name -> Counter.builder(name).register(registry)
        ).increment();
    }

    public void incrementCreate() {
        counters.computeIfAbsent("api.author.create.count",
                name -> Counter.builder(name).register(registry)
        ).increment();
    }

    public void incrementDelete() {
        counters.computeIfAbsent("api.author.delete.count",
                name -> Counter.builder(name).register(registry)
        ).increment();
    }

    public void incrementAddBooks() {
        counters.computeIfAbsent("api.author.add_books.count",
                name -> Counter.builder(name).register(registry)
        ).increment();
    }

    public <T> T recordList(Supplier<T> supplier) {
        return timers.computeIfAbsent("api.author.list.timer",
                name -> Timer.builder(name).register(registry)
        ).record(supplier);
    }

    public <T> void recordCreate(Supplier<T> supplier) {
        timers.computeIfAbsent("api.author.create.timer",
                name -> Timer.builder(name).register(registry)
        ).record(supplier);
    }

    public <T> void recordDelete(Supplier<T> supplier) {
        timers.computeIfAbsent("api.author.delete.timer",
                name -> Timer.builder(name).register(registry)
        ).record(supplier);
    }

    public <T> void recordAddBooks(Supplier<T> supplier) {
        timers.computeIfAbsent("api.author.add_books.timer",
                name -> Timer.builder(name).register(registry)
        ).record(supplier);
    }

}
