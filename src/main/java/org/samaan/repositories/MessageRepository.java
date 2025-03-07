package org.samaan.repositories;

import org.samaan.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findBySenderEmailAndCarrierEmail(String senderEmail, String carrierEmail);
    List<Message> findByCarrierEmailAndSenderEmail(String carrierEmail, String senderEmail);
    List<Message> findByRoomId(String roomId);

    List<Message> findByCarrierEmail(String carrierEmail);

    List<Message> findByRoomIdOrderByTimestampAsc(String roomId);

    List<Message> findBySenderEmailOrCarrierEmail(String senderEmail, String carrierEmail);
}
