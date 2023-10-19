package stc.services.donations.model.donation;

public record CreateDonationRequest(long id, String title, String firstName, String lastName, String sortCode,
                                    int accountNumber, double donationAmount) {
}
