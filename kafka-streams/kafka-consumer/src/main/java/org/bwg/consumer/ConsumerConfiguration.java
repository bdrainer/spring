package org.bwg.consumer;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class ConsumerConfiguration {

    /**
     * consume the numbers received via kafka topic
     * Consumer<T> makes this as kafka consumer of T
     */
    @Bean
    public Consumer<KStream<String, Long>> squaredNumberConsumer() {
        return stream -> stream.foreach((key, value) -> System.out.println("Square Number Consumed : " + value));
    }
}
