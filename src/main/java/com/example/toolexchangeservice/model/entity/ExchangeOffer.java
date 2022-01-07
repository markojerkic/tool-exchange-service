package com.example.toolexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class ExchangeOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;
    @Column(nullable = false)
    private Date suggestedTimeframe;
    @ManyToOne
    @NotNull
    private AdDetail advert;
    @ManyToOne
    @NotNull
    @JsonIgnore
    private UserDetail offerFrom;
}
