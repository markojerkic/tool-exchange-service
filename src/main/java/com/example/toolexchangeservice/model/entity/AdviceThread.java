package com.example.toolexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdviceThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private List<Image> images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AdviceThread that = (AdviceThread) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
