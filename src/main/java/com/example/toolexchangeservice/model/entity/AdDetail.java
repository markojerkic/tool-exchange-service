package com.example.toolexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AdDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    @NotNull
    private String title;
    @Column
    @NotNull
    private String details;
    @Column
    @NotNull
    private Date lastModified;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id")
    @NotNull
    private UserDetail creator;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Tool tool;
    @Column
    @NotNull
    private String phoneNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private List<Image> images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AdDetail adDetail = (AdDetail) o;
        return id != null && Objects.equals(id, adDetail.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
