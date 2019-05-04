package tk.roydgar.restinitializr.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;
import tk.roydgar.restinitializr.util.UrlUtils;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpringInitializrClient {

    private final RestTemplate restTemplate;

    public byte[] getSpringStarterContent(SpringInitializrParameters properties) {
        HttpEntity<Void> httpEntity = new HttpEntity<>(createHeaders());
        String url = UrlUtils.createUrl(properties);

        log.info("Sending request to start.spring.io. Generated url: {}", url);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, byte[].class);
        log.info("Sent request to start.spring.io. Status: {}", response.getStatusCode());

        return response.getBody();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/zip");
        return headers;
    }

}
