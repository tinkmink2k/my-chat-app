package org.example.controller;

import org.example.dto.ParticipantDTO;
import org.example.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService){
        this.participantService = participantService;
    }

    // tao participant moi
    @PostMapping
    public ResponseEntity<ParticipantDTO> createParticipant(@RequestBody ParticipantDTO participantDTO){
        ParticipantDTO createdParticipant = participantService.createParticipant(participantDTO);
        return new ResponseEntity<>(createdParticipant, HttpStatus.CREATED);
    }

    // cap nhat participant voi Id
    @PutMapping("/{participantId}")
    public ResponseEntity<ParticipantDTO> updateParticipant(@PathVariable("participantId") Long participantId, @RequestBody ParticipantDTO participantDTO){
       try {
           ParticipantDTO updatedParticipant = participantService.updateParticipant(participantId, participantDTO);
           return new ResponseEntity<>(updatedParticipant, HttpStatus.CREATED);
       } catch (RuntimeException e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    // xoa participant theo Id
    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable("participantId") Long participantId){
        try {
            participantService.deleteParticipant(participantId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{participantId}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable("participantId") Long participantId){
        try{
            ParticipantDTO participantDTO = participantService.getParticipantById(participantId);
            return new ResponseEntity<>(participantDTO, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants(){
        List<ParticipantDTO> participants = participantService.getAllParticipants();
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<ParticipantDTO>> getParticipantByConversationId(@PathVariable("conversationId") Long conversationId){
        List<ParticipantDTO> participants = participantService.getPaticipantByConversationId(conversationId);
        return new ResponseEntity<>(participants, HttpStatus.OK);
    }
}
