package de.lobo.spring.messaging.config.service;

import de.lobo.spring.messaging.config.ChannelConfig;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(
    defaultRequestChannel = ChannelConfig.PRODUCER_CHANNEL,
    defaultHeaders = @GatewayHeader(name = "contentType", value = "application/json")
)
public interface SqsMessagingGateway {

  /**
   * publishes a message via channel to sqs queue
   */
  void publish(Message msg);
}
