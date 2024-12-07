package ru.yandex.practicum.commerce.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.yandex.practicum.commerce.api.OrderClient;
import ru.yandex.practicum.commerce.api.WarehouseClient;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableDiscoveryClient
@EnableFeignClients(clients = {OrderClient.class, WarehouseClient.class})
public class Delivery {
    public static void main(String[] args) {
        SpringApplication.run(Delivery.class, args);
    }
}
