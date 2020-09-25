package com.sandhusanthakumar.demo.kafka.producer;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableKafka
@EnableScheduling
@SpringBootApplication
public class Application {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final AtomicInteger index = new AtomicInteger();

  public Application(final KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Scheduled(fixedDelay = 1000L)
  private void sendMessage() {
    int value = index.incrementAndGet();
    kafkaTemplate.send("test-topic", value + "");
    System.out.println("Sent: " + value);
  }
}
