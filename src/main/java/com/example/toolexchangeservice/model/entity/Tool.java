package com.example.toolexchangeservice.model.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String name;
    @Column
    private String manufacturerLink;
    @Column
    @NotNull
    private Date lastModified;
    @Column
    @NotNull
    private boolean isElectric;
    @Column
    private boolean hasBattery;
    @Column
    private Long power;
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private ToolState toolState;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tool tool = (Tool) o;
        return id != null && Objects.equals(id, tool.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}