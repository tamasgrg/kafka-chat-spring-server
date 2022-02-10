package com.zrb.kafkachatspringserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

  @Autowired
  SimpMessagingTemplate template;

  @KafkaListener(topics = "${cloudkarafka.topic}")
  public void listen(Message message) {
    System.out.println("sending via kafka listener..");
    template.convertAndSend("/topic/group", message);
  }

}
