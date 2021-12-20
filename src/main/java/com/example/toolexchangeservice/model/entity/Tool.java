package com.example.toolexchangeservice.model.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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


}