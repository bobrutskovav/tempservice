package ru.aleksx.tempservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.aleksx.tempservice.model.Device;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends MongoRepository<Device, String> {

    List<Device> findAllByGroupName(String groupName);

    Optional<Device> findFirstByIp(String ip);
}
