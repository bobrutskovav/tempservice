package ru.aleksx.tempservice.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.aleksx.tempservice.model.TemperatureData;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemperatureRepository extends MongoRepository<TemperatureData, String> {


    List<TemperatureData> findAllBySensorIdOrderByTimeDesc(String sensorId, Pageable pageable);

    Optional<TemperatureData> findFirstBySensorIdOrderByTimeDesc(String sensorId);

    List<TemperatureData> findAllByOrderByTimeDesc(Pageable pageable);

}
