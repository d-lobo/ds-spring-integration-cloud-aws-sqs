package de.lobo.spring.messaging.config.service;

import de.lobo.spring.messaging.config.ChannelConfig;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

  private SqsMessagingGateway gateway;

  @Autowired
  public MessagingService(SqsMessagingGateway gateway) {
    this.gateway = gateway;
  }

  public void sendMessage(String msg) {
    Objects.requireNonNull(msg, "msg must not be null");
    gateway.publish(MessageBuilder.withPayload(msg).build());
  }

  @ServiceActivator(inputChannel = ChannelConfig.PRODUCER_CHANNEL)
  public String receiveMessage(Message message) {
    var msgBody = message.getPayload();

    //do something with payload, e.g. parse to pojo model with jackson

    return msgBody.toString();
  }
}
