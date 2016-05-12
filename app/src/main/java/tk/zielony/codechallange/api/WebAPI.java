package tk.zielony.codechallange.api;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by Marcin on 2016-05-12.
 */
public class WebAPI extends DataAPI {
    public WebAPI() {
        apiUrl = "http://jsonplaceholder.typicode.com";
    }

    private static String apiUrl;

    protected <Type2> Type2 getInternal(final String endpoint, Class<Type2> dataClass) {
        String url = apiUrl + endpoint;
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(converter);

        String response = restTemplate.getForObject(url,String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(response,dataClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
