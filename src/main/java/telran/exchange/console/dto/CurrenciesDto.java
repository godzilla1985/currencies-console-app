package telran.exchange.console.dto;


import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Map;

@Getter
@ToString
public class CurrenciesDto {

    private boolean success;
    private String timestamp;
    private Rate base;
    private LocalDate date;
    private Map<Rate, Double> rates;

}
