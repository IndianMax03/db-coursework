package com.manu.roleplaybackend.model.request;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TestPic {
    
    String login;
    MultipartFile file;
    File dest;
    String filename;
    byte[] bytes;
    
    public void convert() {
        try {
            filename = file.getOriginalFilename();
            bytes = file.getBytes();
        } catch (IOException ex) {
            filename = "НЕ МОГУ ПОЛУЧИТЬ БАЙТЫ";
        }
    }
    
}
