package stc.services.donations.controller;

import stc.services.donations.exception.DonationNotFoundException;
import stc.services.donations.model.donation.DonationResponse;
import stc.services.donations.model.donation.DonationsResponse;
import stc.services.donations.model.donation.CreateDonationRequest;
import stc.services.donations.service.DonationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
/**
 * Class is marked with the RestController annotation.It contains apis with different request URIs to post and get
 * donation details
 *
 * @author Subhakanta Das
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/donations")
public class DonationController {

    private final DonationService donationService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public DonationsResponse getDonations() {
        log.info("getting all Donations");

        return donationService.getDonations();
    }


    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public DonationResponse createDonation(@RequestBody CreateDonationRequest createDonationRequest) {
        log.info("creating donation {}", createDonationRequest);

        DonationResponse donation = donationService.createDonation(createDonationRequest);

        log.info("donation created, donation id {}", donation.id());

        return donation;
    }

    @ExceptionHandler(DonationNotFoundException.class)
    @ResponseStatus(value = NOT_FOUND)
    private void DonationNotFoundException() {
    }
}
