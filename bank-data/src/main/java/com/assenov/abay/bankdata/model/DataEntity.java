package com.assenov.abay.bankdata.model;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "data", schema = "public")
public class DataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "data.generator")
    @SequenceGenerator(name = "data.generator", sequenceName = "SEQ_DATA", allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "updated_timestamp")
    private LocalDateTime updatedTimestamp;
}
