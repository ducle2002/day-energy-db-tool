package com.example.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RabbitMqDto {

    private String exchange;
    private String key;
}
