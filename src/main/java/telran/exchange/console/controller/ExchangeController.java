package telran.exchange.console.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import telran.exchange.console.dto.CurrenciesDto;
import telran.exchange.console.dto.Rate;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class ExchangeController {

    static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.GET,
                URI.create("http://data.fixer.io/api/latest?access_key=af186c99c4a925f51b6d8f222ea80447"));
        ResponseEntity<CurrenciesDto> responseEntity = restTemplate.exchange(requestEntity, CurrenciesDto.class);
        CurrenciesDto currenciesDto = responseEntity.getBody();

        boolean appEnabled = true;

        while (appEnabled) {
            System.out.println("Please choose which currency do you want to exchange");
            System.out.println(Arrays.toString(Rate.values()));
            Scanner scanner = new Scanner(System.in);
            Rate rateFrom = null;
            Rate rateTo = null;
            while (rateFrom == null || rateTo == null) {
                try {
                    rateFrom = Rate.valueOf(scanner.next());
                    System.out.println("Your choose from is: " + rateFrom);

                    System.out.println("Please choose which currency do you want to buy");
                    rateTo = Rate.valueOf(scanner.next());
                    System.out.println("Your choose buying is: " + rateTo);
                } catch (IllegalArgumentException e) {
                    System.out.println("Please type valid currency. " + e.getMessage());
                }

            }
            System.out.println("Please choose how much " + rateTo + " do you want to buy");
            Double numbers = scanner.nextDouble();
            System.out.println("We are calculating your request. Please wait");
            assert currenciesDto != null;
            Double result = calculateCurrency(rateFrom, rateTo, numbers, currenciesDto);
            System.out.println("This is result: You can buy " + numbers + " of " + rateTo + " for " + String.format("%.2f", result) + " " + rateFrom);
            System.out.println("Type \"exit\" if you want exit or type \"next\" to continue");
            String clientDesicion = scanner.next();
            if (clientDesicion.equalsIgnoreCase("exit")) {
                appEnabled = false;
            } else {
                System.out.println("You choose continue with us. We appreciate it!");
            }
        }

    }

    private static Double calculateCurrency(Rate fromRate, Rate toRate, Double numbers, CurrenciesDto currenciesDto) {
        Map<Rate, Double> rates = currenciesDto.getRates();
        Double fromValue = rates.get(fromRate);
        Double toValue = rates.get(toRate);
        return (fromValue / toValue) * numbers;
    }

}
