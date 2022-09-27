package com.cooksys.assessment1.group3.entities.embeddable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Credentials {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}
