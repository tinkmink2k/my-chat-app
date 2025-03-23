package org.example.controller;

import org.example.dto.ConversationDTO;
import org.example.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/conversations")
public class ConversationController {
    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    // tao mot cuoc hoi thoai
    @PostMapping
    public ResponseEntity<ConversationDTO> createConversation(@RequestBody ConversationDTO conversationDTO) {
        ConversationDTO newConversation = conversationService.createConversation(conversationDTO);
        return new ResponseEntity<>(newConversation, HttpStatus.CREATED);
    }

    // cap nhat cuoc goi thoai theo ID
    @PutMapping("/{conversationId}")
    public ResponseEntity<ConversationDTO> updateConversation(@PathVariable Integer conversationId, @RequestBody ConversationDTO conversationDTO) {
        ConversationDTO updatedConversation = conversationService.updateConversation(conversationId, conversationDTO);
        return new ResponseEntity<>(updatedConversation, HttpStatus.OK);
    }

    // xoa cuoc hoi thoai theo id
    @DeleteMapping("/{conversationId}")
    public ResponseEntity<Void> deleteConversation(@PathVariable Integer conversationId) {
        conversationService.deleteConversation(conversationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // tim kiem cuoc hoi thoai theo Id
    @GetMapping("/{conversationId}")
    public ResponseEntity<ConversationDTO> getConversationById(@PathVariable Integer conversationId) {
        Optional<ConversationDTO> conversationDTO = conversationService.getConversationById(conversationId);
        return conversationDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //tim kiem cuoc hoi thoai theo title
    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<ConversationDTO>> getConversationsByTitle(@PathVariable String title) {
        List<ConversationDTO> conversations = conversationService.getConversationsByTitle(title);
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    // tim kiem tat ca cac cuoc hoi thoai
    @GetMapping
    public ResponseEntity<List<ConversationDTO>> getAllConversations() {
        List<ConversationDTO> conversations = conversationService.getAllConversations();
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }

    // tim kiem cac cuoc hoi thoai theo title, creatorid, channelid
    @GetMapping("/search")
    public ResponseEntity<List<ConversationDTO>> searchConversation(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) Long creatorId,
        @RequestParam(required = false) String channelId) {

        List<ConversationDTO> conversations = conversationService.searchConversations(title, creatorId, channelId);
        return new ResponseEntity<>(conversations, HttpStatus.OK);
    }
}
