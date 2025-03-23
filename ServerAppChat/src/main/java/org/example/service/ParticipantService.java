package org.example.service;

import org.example.dto.ParticipantDTO;
import org.example.model.Conversation;
import org.example.model.Participant;
import org.example.model.ParticipantType;
import org.example.model.User;
import org.example.repository.ParticipantRepository;
import org.example.repository.ConversationRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, ConversationRepository conversationRepository, UserRepository userRepository){
        this.participantRepository = participantRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    // tao moi participant
    public ParticipantDTO createParticipant(ParticipantDTO participantDTO){
        Participant participant = convertDTOToEntity(participantDTO);
        participant.setCreatedAt(LocalDateTime.now());
        participant.setUpdatedAt(LocalDateTime.now());
        participant = participantRepository.save(participant);
        return covertEntityToDTO(participant);
    }

    // lay participants theo converstionId
    public List<ParticipantDTO> getPaticipantByConversationId(Long conversationId){
        List<Participant> participants = participantRepository.findByConversation_ConversationId(conversationId);
        return participants.stream().map(this::covertEntityToDTO).collect(Collectors.toList());
    }

    // cap nhat participantId
    public ParticipantDTO updateParticipant(Long participantId, ParticipantDTO participantDTO) {
        // tim participant hien co
        Participant existingParticipant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant not found with id:" + participantId));

        // cap nhat thong tin
        existingParticipant.setType(participantDTO.getType());

        // cap nhat conversation neu can
        if (participantDTO.getConversationId() != null) {
            Conversation conversation = fetchConversationById(participantDTO.getConversationId());
            existingParticipant.setConversation(conversation);
        }

        // cap nhat user neu can
        if (participantDTO.getUserId() != null) {
            User user = fetchUserById(participantDTO.getUserId());
            existingParticipant.setUser(user);
        }

        // cap nhat thoi gian
        existingParticipant.setUpdatedAt(LocalDateTime.now());

        // luu participant da cap nhat
        Participant updatedParticipant = participantRepository.save(existingParticipant);

        // tra ve DTO
        return covertEntityToDTO(updatedParticipant);
    }


    // xoa participant theo id
    public void deleteParticipant(Long participantId){
        Participant existingParticipant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant not found with id:" + participantId));
        participantRepository.delete(existingParticipant);
    }

    // lay participant theo id
    public ParticipantDTO getParticipantById(Long participantId){
        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant not found with id:" + participantId));
        return covertEntityToDTO(participant);
    }

    // lay tat ca participant
    public List<ParticipantDTO> getAllParticipants(){
        List<Participant> participants = participantRepository.findAll();
        return participants.stream().map(this::covertEntityToDTO).collect(Collectors.toList());
    }

    public Conversation fetchConversationById(Long conversationId){
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found with id:" + conversationId));
    }

    public User fetchUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found with id:" + userId));
    }

    private Participant convertDTOToEntity(ParticipantDTO participantDTO){
        Participant participant = new Participant();
        participant.setParticipantId(participantDTO.getParticipantId());
        participant.setType(participantDTO.getType());
        if (participantDTO.getConversationId() != null){
            participant.setConversation(fetchConversationById(participantDTO.getConversationId()));
        }
        if (participantDTO.getUserId() != null){
            participant.setUser(fetchUserById(participantDTO.getUserId()));
        }
        participant.setCreatedAt(participantDTO.getCreatedAt() != null ? participant.getCreatedAt() : LocalDateTime.now());
        participant.setUpdatedAt(participantDTO.getUpdatedAt() != null ? participant.getUpdatedAt() : LocalDateTime.now());

        return participant;
    }

    private ParticipantDTO covertEntityToDTO(Participant participant){
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setParticipantId(participant.getParticipantId());
        participantDTO.setType(participant.getType());
        participantDTO.setConversationId(participant.getConversation().getConversationId());
        participantDTO.setUserId(participant.getUser().getUserId());
        participantDTO.setCreatedAt(participant.getCreatedAt());
        participantDTO.setUpdatedAt(participant.getUpdatedAt());
        return participantDTO;
    }
}
