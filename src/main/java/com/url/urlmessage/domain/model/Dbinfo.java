package com.url.urlmessage.domain.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Repository
//@Table(name="dbinfos")
public class Dbinfo implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @Column(name = "name", length = 50)
    private String name;

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + "}";
    }
}
