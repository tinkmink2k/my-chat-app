package org.example.controller;


import org.example.dto.MessageDTO;
import org.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService = messageService;
    }

    // tao tin nhan moi
    @PostMapping
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO){
        MessageDTO createdMessage = messageService.createdMessage(messageDTO);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    // cap nhat tin nhan theo ID
    @PutMapping("/{messageId}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable("messageId") Long messageId, @RequestBody MessageDTO messageDTO){
        try {
            MessageDTO updatedMessage = messageService.updateMessage(messageId, messageDTO);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //xoa tin nhan theo ID
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable("messageId") Long messageId){
        try {
            messageService.deleteMessage(messageId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //lay tin nhan theo id cua conversation
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<MessageDTO>> getMessagesByConversationId(@PathVariable("conversationId") Long conversationId) {
        List<MessageDTO> messages = messageService.getMessageByConversationId(conversationId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    //lay tin nhan theo id nguoi gui senderid
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<MessageDTO>> getMessageBySenderId(@PathVariable("senderId") Long senderId){
        List<MessageDTO> messages = messageService.getMessageBySenderId(senderId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
