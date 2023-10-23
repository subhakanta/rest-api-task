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

    /**
     * Method to get all the donation details present in the application from the
     * database
     * @return Donation response object having list of reponses.
     */
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public DonationsResponse getDonations() {
        log.info("getting all Donations");

        return donationService.getDonations();
    }

    /**
     * Method to add the donation details that comes from the client into the
     * 	database
     *
     * @param createDonationRequest object which contains the request attributes for
     * 	adding donation details in the application
     * @return donation as a response entity.
     */

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
