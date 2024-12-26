package com.ms.user.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class EmailDto {

	private UUID userId;
    private String emailTo;
    private String subject;
    private String text;
    
    private boolean isHtml;  // Adicione este campo
    
    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean isHtml) {  // Adicione este método
        this.isHtml = isHtml;
    }
}
