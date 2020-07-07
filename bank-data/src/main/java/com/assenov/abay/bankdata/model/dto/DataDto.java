package com.assenov.abay.bankdata.model.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DataDto {

    @CsvBindByName(column = "PRIMARY_KEY")
    private Long id;

    @CsvBindByName(column = "NAME")
    private String name;

    @CsvBindByName(column = "DESCRIPTION")
    private String description;

    @CsvBindByName(column = "UPDATED_TIMESTAMP")
    private String updatedTimestamp;
}
