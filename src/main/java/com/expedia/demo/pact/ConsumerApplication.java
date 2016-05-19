package com.expedia.demo.pact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
@RestController
@RequestMapping("consumer")
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

    @RequestMapping("sayit")
    public Message sayIt() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://localhost:8080/message", Message.class);
    }

    public static class Message {
        private String text;

        public Message(String text) {
            this.text = text;
        }

        public Message() {
        }

        public String getText() {
            return text;
        }
    }
}
