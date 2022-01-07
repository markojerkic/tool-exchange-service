package com.example.toolexchangeservice.model.entity;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotNull
    private String message;
    @Column
    @NotNull
    private Date lastModified;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "creator_id")
    @NotNull
    private UserDetail creator;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "thread_id")
    @NotNull
    private AdviceThread parentThread;
    @Column
    private Boolean isLiked = false;

}
