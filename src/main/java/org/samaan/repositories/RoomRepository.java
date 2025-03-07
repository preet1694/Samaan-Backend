package org.samaan.repositories;


import org.samaan.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoomRepository extends MongoRepository<Room, String>{
    Room findByRoomId(String roomId);

    Optional<Room> findBySenderEmailAndCarrierEmail(String sender, String carrier);
}
