package de.lobo.spring.messaging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class ChannelConfig {
  public final static String CONSUMER_CHANNEL = "consumerChannel";
  public final static String PRODUCER_CHANNEL = "producerChannel";
  public final static String ERROR_CHANNEL = "errorChannel";

  @Bean(CONSUMER_CHANNEL)
  public MessageChannel consumerChannel() {
    return MessageChannels.direct().get();
  }

  @Bean(PRODUCER_CHANNEL)
  public MessageChannel producerChannel() {
    return MessageChannels.direct().get();
  }

  @Bean(ERROR_CHANNEL)
  public MessageChannel errorChannel() {
    return MessageChannels.direct().get();
  }
}
