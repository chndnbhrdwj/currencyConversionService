package com.cns.currencyConversionService;

import com.cns.currencyConversionService.model.CurrencyConverted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private Environment environment;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConverted retreive(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        ResponseEntity<CurrencyConverted> currencyConvertedResponseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConverted.class, uriVariables);
        CurrencyConverted body = currencyConvertedResponseEntity.getBody();
        return new CurrencyConverted(body.getId(), body.getFrom(), body.getTo(),
                quantity, body.getConversionMultiple(), quantity.multiply(body.getConversionMultiple()),
                body.getEnvironment());
    }
}
