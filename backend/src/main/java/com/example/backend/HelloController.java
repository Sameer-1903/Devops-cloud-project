package com.example.backend;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
public class HelloController {

  private final Counter requestCounter;
  private final Timer requestTimer;
  private final PrometheusMeterRegistry prometheusRegistry;

  public HelloController(io.micrometer.core.instrument.MeterRegistry registry,
                         PrometheusMeterRegistry prometheusRegistry) {
    this.requestCounter = Counter.builder("app_http_requests_total")
      .description("Total number of HTTP requests")
      .register(registry);

    this.requestTimer = Timer.builder("app_request_duration_seconds")
      .description("Request duration in seconds")
      .publishPercentiles(0.5, 0.95)
      .register(registry);

    this.prometheusRegistry = prometheusRegistry;
  }

  @GetMapping("/api/hello")
  public Map<String,String> hello() {
    long start = System.nanoTime();
    try {
      requestCounter.increment();
      return Map.of("message", "Hello from backend!");
    } finally {
      long duration = System.nanoTime() - start;
      requestTimer.record(Duration.ofNanos(duration));
    }
  }

  @GetMapping(value = "/metrics", produces = MediaType.TEXT_PLAIN_VALUE)
  public String metrics() {
    return prometheusRegistry.scrape();
  }
}
