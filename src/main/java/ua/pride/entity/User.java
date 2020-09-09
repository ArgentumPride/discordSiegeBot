package ua.pride.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rainbow_user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "dis_tag", unique = true)
    private String disTag;

    @Column(name = "siege_username", unique = true)
    private String siegeUsername;

    @Column(name = "rank")
    private String rank;

    public User(String disTag, String siegeUsername, String rank) {
        this.disTag = disTag;
        this.siegeUsername = siegeUsername;
        this.rank = rank;
    }
}
