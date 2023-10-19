package stc.services.donations.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@Entity
@Table(name = "Donations")
public class Donation {

    public Donation() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String firstName;

    private String lastName;

    private String sortCode;

    private int accountNumber;

    private double donationAmount;
}
