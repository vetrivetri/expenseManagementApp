package com.expense.management.app.expenseManagementApp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestClient;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {"com.expense.management.app.expenseManagementApp","com.expense.management.app.expenseManagementApp.entity"})
@EnableAutoConfiguration
@EntityScan("com.expense.management.app.expenseManagementApp.entity")
public class ExpenseManagementAppApplication {

    @Value("${baseurl}")
    private String baseUrl;

	public static void main(String[] args) {
		SpringApplication.run(ExpenseManagementAppApplication.class, args);
	}


    @Bean
    public RestClient restClient() {
         return RestClient.builder()
                .baseUrl(baseUrl) // Use the injected value
                .build();
    }
}
