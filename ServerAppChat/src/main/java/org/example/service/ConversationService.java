package org.example.service;

import org.example.dto.ConversationDTO;
import org.example.model.Conversation;
import org.example.model.User;
import org.example.repository.ConversationRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    // tao cuoc tro chuyen moi
    public ConversationDTO createConversation(ConversationDTO conversationDTO) {
        Conversation conversation = convertDTOtoEntity(conversationDTO);
        conversation = conversationRepository.save(conversation);
        return convertEntityToDTO(conversation);
    }

    // cap nhat thong tin cuoc tro chuyen
    public ConversationDTO updateConversation(Integer conversationId, ConversationDTO conversationDTO) {
        Optional<Conversation> existingConversation = conversationRepository.findById(conversationId);
        if (existingConversation.isPresent()) {
            Conversation conversation = existingConversation.get();
            conversation.setTitle(conversationDTO.getTitle());
            conversation.setChannelId(conversationDTO.getChannelId());
            conversation = conversationRepository.save(conversation);
            return convertEntityToDTO(conversation);
        } else {
            throw new RuntimeException("Conversation not found");
        }
    }

    // xoa cuoc tro chuyen
    public void deleteConversation(Integer conversationId) {
        if (conversationRepository.existsById(conversationId)) {
            conversationRepository.deleteById(conversationId);
        } else {
            throw new RuntimeException("Conversation not found");
        }
    }

    // tim kiem cuoc tro chuyen theo id
    public Optional<ConversationDTO> getConversationById(Integer conversationId) {
        Optional<Conversation> conversation = conversationRepository.findById(conversationId);
        return conversation.map(this::convertEntityToDTO);
    }

    // tim kiem cuoc tro chuyen theo creatorid
    public List<ConversationDTO> getConversationsByCreatorId(Long creatorId) {
        List<Conversation> conversations = conversationRepository.findByCreator_UserId(creatorId);
        return conversations.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    // tijm kiem cuoc tro chuyen theo title
    public List<ConversationDTO> getConversationsByTitle(String title) {
        List<Conversation> conversations = conversationRepository.findByTitleContaining(title);
        return conversations.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    public Optional<ConversationDTO> getConversationByChannelId(String channelId) {
        Optional<Conversation> conversation = conversationRepository.findByChannelId(channelId);
        return conversation.map(this::convertEntityToDTO);
    }

    // tim kiem tat ca cac cuoc hoi thoai
    public List<ConversationDTO> getAllConversations(){
        List<Conversation> conversations = conversationRepository.findAll();
        return conversations.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    // tim kiem theo title, channelid, creatorId
    public List<ConversationDTO> searchConversations(String title, Long creatorId, String channelId) {
        List<Conversation> conversations = conversationRepository.findByTitleAndCreatorIdAndChannelId(title, creatorId, channelId);
        return conversations.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private Conversation convertDTOtoEntity(ConversationDTO conversationDTO) {
        Conversation conversation = new Conversation();
        conversation.setConversationId(conversationDTO.getConversationId());
        conversation.setTitle(conversationDTO.getTitle());
        conversation.setChannelId(conversationDTO.getChannelId());

        Optional<User> creator = userRepository.findById(conversationDTO.getCreatorId());
        creator.ifPresent(conversation::setCreator);

        conversation.setCreatedAt(conversationDTO.getCreatedAt());
        conversation.setUpdatedAt(conversationDTO.getUpdatedAt());
        conversation.setDeletedAt(conversationDTO.getDeletedAt());

        return conversation;
    }

    private ConversationDTO convertEntityToDTO(Conversation conversation) {
        ConversationDTO conversationDTO = new ConversationDTO();
        conversationDTO.setConversationId(conversation.getConversationId());
        conversationDTO.setTitle(conversation.getTitle());
        conversationDTO.setChannelId(conversation.getChannelId());
        conversationDTO.setCreatedAt(conversation.getCreatedAt());
        conversationDTO.setUpdatedAt(conversation.getUpdatedAt());
        conversationDTO.setDeletedAt(conversation.getDeletedAt());
        return conversationDTO;
    }
}
