package com.example.toolexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "advert_tools",
            joinColumns = @JoinColumn(name = "advert_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id")
    )
    private List<Tool> tools;
    @Column
    @NotNull
    private String phoneNumber;
}
