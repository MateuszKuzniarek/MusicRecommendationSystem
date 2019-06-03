package com.borsuki.app.musicrecommendationsystem.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "Likes")
@Getter
@Setter
@ToString
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user_id;


    @Column(name = "artist_id")
    private String artist_id;


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Likes)) {
            return false;
        }

        Likes like = (Likes) object;
        return getId().equals(like.getId());
    }
}
