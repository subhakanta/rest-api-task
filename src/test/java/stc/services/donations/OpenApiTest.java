package stc.services.donations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

@DonationServiceSpringTest
class OpenApiTest {

    @Autowired
    private WebTestClient httpClient;


    @Test
    void returnsOpenApiJson() {
        httpClient.get().uri("/v3/api-docs")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                //
                .jsonPath("$.openapi").isEqualTo("3.0.1")
                .jsonPath("$.info.title").isEqualTo("OpenAPI definition");
    }

}
