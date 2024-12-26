package com.ms.user.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.UserModel;
@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void publishMessageEmail(UserModel userModel) {
        if (userModel != null && userModel.getEmail() != null) {
            EmailDto emailDto = new EmailDto();
            emailDto.setUserId(userModel.getUserId());
            emailDto.setEmailTo(userModel.getEmail());
            emailDto.setSubject("Cadastro realizado com sucesso!");

            // Conteúdo do e-mail com HTML
            String htmlContent = "<html>" +
            	    "<head>" +
            	    "    <style>" +
            	    "        .email-body {" +
            	    "            background-color: #f4f4f4;" +
            	    "            padding: 20px;" +
            	    "            font-family: Arial, sans-serif;" +
            	    "        }" +
            	    "        .email-container {" +
            	    "            background-color: #002366;" + // Azul marinho
            	    "            padding: 20px;" +
            	    "            border-radius: 5px;" +
            	    "            max-width: 600px;" +
            	    "            margin: 0 auto;" +
            	    "            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);" +
            	    "            color: #cccccc;" + // Ajustado para um tom cinza claro
            	    "        }" +
            	    "        .email-header {" +
            	    "            color: #ffffff;" +
            	    "            font-size: 24px;" +
            	    "            margin-bottom: 10px;" +
            	    "        }" +
            	    "        .email-footer {" +
            	    "            color: #b3b3b3;" + // Tom mais claro para o rodapé
            	    "            font-size: 12px;" +
            	    "            margin-top: 20px;" +
            	    "        }" +
            	    "    </style>" +
            	    "</head>" +
            	    "<body class='email-body'>" +
            	    "    <div class='email-container'>" +
            	    "        <h1 class='email-header'>Cadastro realizado com sucesso!</h1>" +
            	    "        <p>Olá, <strong>" + userModel.getName() + "</strong>!</p>" +
            	    "        <p>Seja bem-vindo(a) a empresa -MILLENNIUM SOFT- de Gabriel Oliveira! Agradecemos o seu cadastro, e agora você pode aproveitar todos os recursos da nossa plataforma.</p>" +
            	    "        <p>Atenciosamente,</p>" +
            	    "        <p><strong>Equipe da Plataforma</strong></p>" +
            	    "        <div class='email-footer'>Se você tiver alguma dúvida, entre em contato conosco "
            	    + "(28)99999-9999.</div>" +
            	    "    </div>" +
            	    "</body>" +
            	    "</html>";


            // Defina o conteúdo HTML no corpo do e-mail
            emailDto.setText(htmlContent);
            emailDto.setHtml(true);  // Certifique-se de que o e-mail seja interpretado como HTML

            // Envia a mensagem para a fila com a chave de roteamento
            rabbitTemplate.convertAndSend(routingKey, emailDto);
        }
    }

}
