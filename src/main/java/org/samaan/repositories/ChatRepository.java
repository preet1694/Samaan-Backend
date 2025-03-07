// File: backend/src/main/java/com/example/deliveryapp/repository/ChatRepository.java
package org.samaan.repositories;

import org.samaan.model.Chat;
import org.samaan.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

//import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<Message, String> {
   // List<Message> findByParticipantsContaining(String userId);
    List<Message> findByRoomId(String roomId);
    Chat save(Chat chat);

}