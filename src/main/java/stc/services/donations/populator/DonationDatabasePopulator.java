package stc.services.donations.populator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import stc.services.donations.repository.DonationRepository;
import stc.services.donations.repository.model.Donation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
/**
 * Populates donation data stored in the url provided into temp database .
 *
 * @author Subhakanta Das
 */
@Slf4j
@Component
public class DonationDatabasePopulator {

    private final String DonationsUrl;
    private final DonationRepository donationRepository;

    public DonationDatabasePopulator(@Value("${Donations.populator.url}") String DonationsUrl, DonationRepository donationRepository) {
        this.DonationsUrl = DonationsUrl;
        this.donationRepository = donationRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void populateDonationDatabase() throws Exception {
        log.info("populating Donation database from {}", DonationsUrl);

        List<DonationPopulatorResponse> DonationsToPopulate = new ObjectMapper().readValue(new URL(DonationsUrl), new TypeReference<>() {
        });

        List<Donation> donations = DonationsToPopulate.stream()
                .map(donation -> Donation.builder()
                        .title(donation.title)
                        .lastName(donation.lastName)
                        .firstName(donation.firstName)
                        .sortCode(donation.sortCode)
                        .accountNumber(donation.accountNumber)
                        .build())
                .toList();

        donationRepository.saveAll(donations);

        log.info("Donation database populated with {} Donations", donations.size());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private record DonationPopulatorResponse(String title, String lastName, String firstName, String sortCode, int accountNumber
    ,Double donationAmount) {
    }
}
