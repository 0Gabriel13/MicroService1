package com.ms.user.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ms.user.models.UserModel;
import com.ms.user.producers.UserProducer;
import com.ms.user.repositories.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional
    public UserModel save(@Valid UserModel userModel){
        // Verifica se o e-mail já existe no banco de dados
        if(userRepository.existsByEmail(userModel.getEmail())) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
        }
        
        // Salva o usuário no banco
        userModel = userRepository.save(userModel);
        
        // Envia o e-mail
        userProducer.publishMessageEmail(userModel);
        
        return userModel;
    }

    
    @Transactional
    public void deleteById(UUID userId) {
    	if (userRepository.existsById(userId)) {
			userRepository.deleteById(userId);
		}
    	else {
			throw new RuntimeException("Usuário não encontrado para o ID: "+ userId);
		}
    }

}
