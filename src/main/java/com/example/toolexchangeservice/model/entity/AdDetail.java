package com.example.toolexchangeservice.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AdDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    @NotNull
    private String title;
    @Column
    @NotNull
    private String details;
    @Column
    @NotNull
    private Date lastModified;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    @NotNull
    private UserDetail creator;
}
