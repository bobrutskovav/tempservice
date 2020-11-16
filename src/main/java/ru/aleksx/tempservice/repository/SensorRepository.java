package ru.aleksx.tempservice.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.aleksx.tempservice.model.Sensor;

import java.util.UUID;

@Repository
public interface SensorRepository extends CassandraRepository<Sensor, UUID> {
}
