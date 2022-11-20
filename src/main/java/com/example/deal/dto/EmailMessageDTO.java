package com.example.deal.dto;
import com.example.deal.enumeration.Theme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class EmailMessageDTO {

    private String address;
    private Theme theme;
    private Long applicationID;

}
