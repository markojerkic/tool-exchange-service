package com.example.toolexchangeservice.model.entity;

import com.example.toolexchangeservice.model.constants.ExchangeOfferStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
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

    @Column
    @Enumerated(EnumType.STRING)
    private ExchangeOfferStatus offerStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExchangeOffer that = (ExchangeOffer) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
