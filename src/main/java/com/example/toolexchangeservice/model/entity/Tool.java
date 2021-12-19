package com.example.toolexchangeservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTool;
    @Column
    @NotNull
    private String nameTool;
    @Column
    private String manufacturerLink;
    @Column
    @NotNull
    private Date lastModified;
    @Column
    @NotNull
    private long idToolType;  //FK
    @Column
    @NotNull
    private long idToolCategory;  //FK


}