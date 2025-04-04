package easyfit.services.impl;

import org.springframework.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class EmailServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${n8n.webhook.url}")
    private String n8nWebhookUrl;

    
    public void enviarCredenciales(String email, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(payload, headers);

        try {
            restTemplate.postForEntity(n8nWebhookUrl, request, String.class);
            System.out.println("Correo enviado desde EmailServiceImpl");
        } catch (Exception e) {
            System.err.println("Error al enviar desde n8n: " + e.getMessage());
        }
    }
}
