// EmailConsumer.java
package com.ms.email.consumers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.dtos.EmailRecordDto;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;

import lombok.experimental.var;

@Component
public class EmailConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);
    
    final EmailService emailService;
    
    public EmailConsumer(EmailService emailService) {
    	this.emailService = emailService;
    }

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
//        if (emailRecordDto != null && emailRecordDto.emailTo() != null) {
//            logger.info("Email para: {}", emailRecordDto.emailTo());
//            System.out.println("Email para: " + emailRecordDto.emailTo());
//        } else {
//            logger.warn("Mensagem recebida sem destinatário: {}", emailRecordDto);
//        }
    	var emailModel = new EmailModel();
    	BeanUtils.copyProperties(emailRecordDto, emailModel);
    	emailService.sendEmail(emailModel);
    }
}
