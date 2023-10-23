package stc.services.donations.controller;

import stc.services.donations.DonationServiceSpringTest;
import stc.services.donations.repository.DonationRepository;
import stc.services.donations.repository.model.Donation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Map;

import static java.lang.Long.parseLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@DonationServiceSpringTest
class DonationControllerTest {


    @Autowired
    private WebTestClient httpClient;

    @Autowired
    private DonationRepository donationRepository;

    @BeforeEach
    void deleteAllDonationsFromDatabase() {
        donationRepository.deleteAllInBatch();
    }


    @Nested
    public class GetDonations {



        @Test
        void returnsUnauthorizedWhenAuthorizationHeaderIsMissing() {

            getDonationsRequest()
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody().isEmpty();
        }

        @Test
        void returnsUnauthorizedWhenUsernameAndPasswordAreIncorrect() {

            getDonationsRequest()
                    .headers(headers -> headers.setBasicAuth("some-incorrect-username", "some-incorrect-password"))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody().isEmpty();
        }

        private WebTestClient.RequestHeadersSpec<?> getDonationsRequest() {
            return httpClient.get().uri("/donations");
        }
    }


    @Nested
    public class CreateDonation {

        @Test
        void createDonation() {

            createDonationRequest()
                    .headers(DonationControllerTest.this::withBasicAuth)
                    .contentType(APPLICATION_JSON)
                    .body(BodyInserters.fromValue(Map.of(
                            "title", "Mr",
                            "firstName", "David",
                            "lastName", "woodman",
                            "sortCode", "40-35-02",
                            "accountNumber", "1233",
                            "donationAmount", "1212.24"
                    )))
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(APPLICATION_JSON)
                    .expectBody()
                    //
                    .jsonPath("$.title").isEqualTo("Mr")
                    .jsonPath("$.firstName").isEqualTo("David")
                    .jsonPath("$.lastName").isEqualTo("woodman")
                    .jsonPath("$.sortCode").isEqualTo("40-35-02")
                    .jsonPath("$.accountNumber").isEqualTo(1233)
                    .jsonPath("$.donationAmount").isEqualTo(1212.24)
                    .jsonPath("$.id").value(id -> {
                        Donation savedDonation = donationRepository.findById(parseLong(id.toString())).get();
                        assertThat(savedDonation)
                                .isEqualTo(new Donation(parseLong(id.toString()), "Mr", "David","woodman", "40-35-02", 1233, 1212.24));
                    });
        }

        @Test
        void returnsUnauthorizedWhenAuthorizationHeaderIsMissing() {

            createDonationRequest()
                    .contentType(APPLICATION_JSON)
                    .body(BodyInserters.fromValue(Map.of(
                            "title", "Mr",
                            "lastName", "Das",
                            "firstName", "Subhakanta",
                            "sortCode", "45-02-65"
                    )))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody().isEmpty();
        }

        @Test
        void returnsUnauthorizedWhenUsernameAndPasswordAreIncorrect() {

            createDonationRequest()
                    .headers(headers -> headers.setBasicAuth("some-incorrect-username", "some-incorrect-password"))
                    .contentType(APPLICATION_JSON)
                    .body(BodyInserters.fromValue(Map.of(
                            "title", "Mr",
                            "firstName", "Das",
                            "lastName", "new title"
                    )))
                    .exchange()
                    .expectStatus().isUnauthorized()
                    .expectBody().isEmpty();
        }

        private WebTestClient.RequestBodySpec createDonationRequest() {
            return httpClient.post().uri("/donations");
        }
    }


    private void withBasicAuth(HttpHeaders headers) {
        headers.setBasicAuth("Donation-user-test", "Donation-password-test");
    }
}
