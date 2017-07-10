package com.tikal.rm.ci.service;

import com.tikal.rm.ci.CiConfiguration;
import com.tikal.rm.ci.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void produce(String githubUrl,   // serves as unique id
                        String jenkinsUrl, String status) {

        Message message = new Message(githubUrl, jenkinsUrl, status);

        logger.debug("sending message to rabbit");
        rabbitTemplate.convertAndSend(message);
        //rabbitTemplate.convertAndSend(CiConfiguration.queueName, "Hello from producer");
        logger.debug("sent");
    }
}
