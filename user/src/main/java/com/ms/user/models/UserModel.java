package com.ms.user.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable{
	private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy= GenerationType.AUTO)
	    private UUID userId;
	    private String name;
	    
		// @jakarta.validation.constraints.Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$" , message = "O email deve fornecer um endereço @gmail.com")
	    private String email;
	
	    public void setEmail(String email) {
	        this.email = email;
	    }
	
}
