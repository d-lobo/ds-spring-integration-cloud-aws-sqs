package de.lobo.spring.messaging.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.aws.inbound.SqsMessageDrivenChannelAdapter;
import org.springframework.integration.aws.outbound.SqsMessageHandler;
import org.springframework.integration.core.MessageProducer;
import org.springframework.messaging.MessageChannel;

@Configuration
@Profile("!test")
public class SQSConfig {

  @Value("${sqs.queue.producer}")
  private String producerSqsUrl;

  @Value("${sqs.queue.consumer}")
  private String consumerSqsUrl;

  @Bean
  public AmazonSQSAsync amazonSqs() {
    return AmazonSQSAsyncClientBuilder.defaultClient();
  }

  @Bean
  public MessageProducer producerSqsAdapter(AmazonSQSAsync amazonSqs,
      @Qualifier(ChannelConfig.PRODUCER_CHANNEL) MessageChannel channel) {
    SqsMessageDrivenChannelAdapter adapter = new SqsMessageDrivenChannelAdapter(amazonSqs,
        producerSqsUrl);
    adapter.setOutputChannel(channel);
    adapter.setErrorChannelName(ChannelConfig.ERROR_CHANNEL);
    return adapter;
  }

  @Bean
  @ServiceActivator(inputChannel = ChannelConfig.CONSUMER_CHANNEL)
  public SqsMessageHandler consumerSqsAdapter(AmazonSQSAsync amazonSqs) {
    SqsMessageHandler messageHandler = new SqsMessageHandler(amazonSqs);
    messageHandler.setQueue(consumerSqsUrl);
    return messageHandler;
  }

}
