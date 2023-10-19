package stc.services.donations.populator;

import stc.services.donations.DonationServiceSpringTest;
import stc.services.donations.repository.DonationRepository;
import stc.services.donations.repository.model.Donation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DonationServiceSpringTest
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:Donation-service-populator")
class DonationDatabasePopulatorTest {

    @Autowired
    private DonationRepository donationRepository;

    @Test
    void populatesDatabaseWhenApplicationStarts() {

        List<Donation> Donations = donationRepository.findAll();

        assertThat(Donations).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id","donationAmount")
                .containsExactlyInAnyOrder(
                        Donation.builder().title("Mr").firstName("David").lastName("Jane").sortCode("20-20-09").accountNumber(124343445).donationAmount(0.00).build(),
                        Donation.builder().title("Mr").firstName("David").lastName("Goodman").sortCode("20-20-09").accountNumber(51887654).donationAmount(0.00).build()



                );
    }

}
