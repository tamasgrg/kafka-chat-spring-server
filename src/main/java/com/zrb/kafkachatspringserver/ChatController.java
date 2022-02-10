package com.zrb.kafkachatspringserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
public class ChatController {

  @Autowired
  private KafkaTemplate<String, Message> kafkaTemplate;

  @Value("${cloudkarafka.topic}")
  private String topic;

  @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
  public void sendMessage(@RequestBody Message message) {
    message.setTimestamp(LocalDateTime.now().toString());
    kafkaTemplate.send(topic, message);
  }

  @MessageMapping("/sendMessage")
  @SendTo("/topic/group")
  public Message broadcastGroupMessage(@Payload Message message) {
    return message;
  }

}
