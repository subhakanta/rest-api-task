package stc.services.donations.service;

import stc.services.donations.model.donation.DonationResponse;
import stc.services.donations.model.donation.DonationsResponse;
import stc.services.donations.model.donation.CreateDonationRequest;
import stc.services.donations.repository.model.Donation;
import stc.services.donations.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Slf4j
@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationsResponse getDonations() {
        return new DonationsResponse(donationRepository.findAll().stream()
                .map(this::donationResponse)
                .sorted(Comparator.comparing(DonationResponse::title))
                .collect(toList()));
    }

    public DonationResponse createDonation(CreateDonationRequest createDonationRequest) {
        Donation donation = donationRepository.save(Donation.builder()
                .title(createDonationRequest.title()).lastName(createDonationRequest.lastName())
                .firstName(createDonationRequest.firstName()).sortCode(createDonationRequest.sortCode())
                .accountNumber(createDonationRequest.accountNumber()).donationAmount(createDonationRequest.donationAmount())
                .build());
        return donationResponse(donation);
    }

    private DonationResponse donationResponse (Donation donation) {
        return new DonationResponse(donation.getId(), donation.getTitle(), donation.getLastName(),donation.getFirstName(),donation.getSortCode()
                ,donation.getAccountNumber(),donation.getDonationAmount());
    }

}
