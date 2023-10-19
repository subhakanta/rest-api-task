package stc.services.donations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.containsString;

@DonationServiceSpringTest
class SwaggerUITest {

    @Autowired
    private WebTestClient httpClient;


    @Test
    void returnsSwaggerUIPage() {
        httpClient.get().uri("/swagger-ui/index.html")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(containsString("<title>Swagger UI</title>"));
    }

}
