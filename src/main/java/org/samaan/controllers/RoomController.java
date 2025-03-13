package org.samaan.controllers;

import org.samaan.model.Message;
import org.samaan.model.Room;
import org.samaan.repositories.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@CrossOrigin("https://samaan-rho.vercel.app/")
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // Create or get existing room
    @PostMapping
    public ResponseEntity<?> createOrGetRoom(@RequestParam String senderEmail, @RequestParam String carrierEmail) {
        // Generate a room ID in a consistent way (sorted order to prevent duplicates)
        String roomId = senderEmail.compareTo(carrierEmail) < 0 ?
                senderEmail + "_" + carrierEmail : carrierEmail + "_" + senderEmail;

        Room existingRoom = roomRepository.findByRoomId(roomId);
        if (existingRoom != null) {
            return ResponseEntity.ok(existingRoom);
        }

        // Create a new room if it does not exist
        Room newRoom = new Room();
        newRoom.setRoomId(roomId);
        Room savedRoom = roomRepository.save(newRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoom);
    }

    // Get room
    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().body("Room not found");
        }
        return ResponseEntity.ok(room);
    }

    // Get messages of room with pagination
    @GetMapping("/{roomId}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String roomId,
                                                     @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                     @RequestParam(value = "size", defaultValue = "20", required = false) int size) {
        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Message> messages = room.getMessages();
        int start = Math.max(0, messages.size() - (page + 1) * size);
        int end = Math.min(messages.size(), start + size);

        List<Message> paginatedMessages = messages.subList(start, end);
        return ResponseEntity.ok(paginatedMessages);
    }
}
