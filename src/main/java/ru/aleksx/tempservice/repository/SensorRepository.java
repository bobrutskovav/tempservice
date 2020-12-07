package ru.aleksx.tempservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.aleksx.tempservice.model.Sensor;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends MongoRepository<Sensor, String> {

    List<Sensor> findAllByGroupName(String groupName);

    Optional<Sensor> findFirstByIp(String ip);
}
