package de.lobo.spring.messaging;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.lobo.spring.messaging.config.ChannelConfig;
import de.lobo.spring.messaging.config.service.MessagingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MessagingGatewayTest {

  @MockBean
  private MessagingService serviceMock;

  @Autowired
  @Qualifier(ChannelConfig.CONSUMER_CHANNEL)
  private MessageChannel consumerChannel;

  @Test
  public void testConsumeMessage() {
    consumerChannel.send(MessageBuilder.withPayload("hello world!").build());

    //validate if given service was triggered by @ServiceActivator
    verify(serviceMock, times(1))
        .receiveMessage(eq(MessageBuilder.withPayload("hello world!").build()));

  }
}
