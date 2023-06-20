package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="country")
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  int id;
    @Column(name = "code")
    private  String code;
    @Column(name = "name")
    private  String  name;
    //RelationShip for database
    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<State> states;
}
