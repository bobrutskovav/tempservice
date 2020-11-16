package ru.aleksx.tempservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
public class Sensor {

    @PrimaryKeyColumn(name = "id",type = PrimaryKeyType.PARTITIONED)
    private UUID id;


    @Column
    private String ip;




}
