package ru.aleksx.tempservice.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import ru.aleksx.tempservice.model.TempData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemperatureRepository extends CassandraRepository<TempData, UUID> {

    @Query("Select * FROM temperature WHERE sensorId = :sensorId limit :limit ORDER BY time ASC")
    List<TempData> getLastNForSensorId(UUID sensorId, int limit);

    @Query("Select * FROM temperature WHERE sensorId = :sensorId limit 1 ORDER BY time ASC")
    Optional<TempData> getLastForSensorId(UUID sensorId);

    @Query("Select * FROM temperature limit :limit ORDER BY time ASC")
    List<TempData> getLastNForAll(int limit);
}
