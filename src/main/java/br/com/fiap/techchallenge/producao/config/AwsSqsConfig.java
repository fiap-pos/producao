package br.com.fiap.techchallenge.producao.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class AwsSqsConfig {
    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient, ObjectMapper objectMapper){
        objectMapper.registerModule(new JavaTimeModule());

        return SqsTemplate.builder().configureDefaultConverter(converter -> {
                    converter.setObjectMapper(objectMapper);
                }).sqsAsyncClient(sqsAsyncClient)
                .build();
    }
}
