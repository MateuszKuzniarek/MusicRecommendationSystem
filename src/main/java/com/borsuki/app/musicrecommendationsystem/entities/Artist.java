package com.borsuki.app.musicrecommendationsystem.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) { return true; }
        if (!(object instanceof Artist)) { return false; }

        Artist artist = (Artist) object;

        return getName().equals(artist.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
