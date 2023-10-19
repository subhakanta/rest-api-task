package stc.services.donations.model.donation;

public record DonationResponse(long id, String title, String lastName, String firstName, String sortCode,
                               int accountNumber, double donationAmount) {
}
