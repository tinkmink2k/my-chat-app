package org.example.service;

import org.example.dto.MessageDTO;
import org.example.model.Conversation;
import org.example.model.Message;
import org.example.model.MessageType;
import org.example.model.User;
import org.example.repository.ConversationRepository;
import org.example.repository.MessageRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository){
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    // tao tin nhan moi
    public MessageDTO createdMessage(MessageDTO messageDTO){
        Conversation conversation = conversationRepository.findById(messageDTO.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        User sender = userRepository.findById(messageDTO.getSenderId())
                .orElseThrow(()->new RuntimeException("Sender not found"));

        Message message = convertDTOToEntity(messageDTO, conversation, sender);

        message = messageRepository.save(message);

        return convertEntityToDTO(message);
    }

    // cap nhat tin nhan moi
    public MessageDTO updateMessage(Long messageId, MessageDTO messageDTO){
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isPresent()){
            Message message = existingMessage.get();
            message.setMessage(messageDTO.getMessage());
            message.setMessageType(messageDTO.getMessageType());
            message = messageRepository.save(message);
            return convertEntityToDTO(message);
        } else {
            throw new RuntimeException("Message not found");
        }
    }

    // xoa tin nhan
    public void deleteMessage(Long messageId){
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isPresent()){
            messageRepository.deleteById(messageId);
        } else {
            throw new RuntimeException("Message Not Found");
        }
    }

    //lay tin nhan theo id
    public Optional<MessageDTO> getMessageById(Long messageId){
        Optional<Message> message = messageRepository.findById(messageId);
        return message.map(this::convertEntityToDTO);
    }

    //get all message in coversation
    public List<MessageDTO> getMessageByConversationId(Long conversationId) {
        List<Message> messages = messageRepository.findByConversation_ConversationId(conversationId);
        return messages.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    //get all message by user
    public List<MessageDTO> getMessageBySenderId(Long senderId) {
        List<Message> messages = messageRepository.findBySender_UserId(senderId);
        return messages.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    //get messages by conversation and message type
    public List<MessageDTO> getMessagesByConversationAndMessageType(Long conversationId, MessageType messageType){
        List<Message> messages = messageRepository.findByConversation_ConversationIdAndMessageType(conversationId, messageType);
        return messages.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    private Message convertDTOToEntity(MessageDTO messageDTO, Conversation conversation, User sender){
        Message message = new Message();
        message.setMessageId(messageDTO.getMessageId());
        message.setMessage(messageDTO.getMessage());
        message.setMessageType(messageDTO.getMessageType());
        message.setCreatedAt(messageDTO.getCreatedAt());

        //gan doi tuong conversation duoc lay ra tu DB
        message.setConversation(conversation);

        //gan doi tuong user duoc lay ra tu db
        message.setSender(sender);

        return message;
    }

    private MessageDTO convertEntityToDTO(Message message){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(message.getMessageId());
        messageDTO.setMessage(message.getMessage());
        messageDTO.setMessageType(message.getMessageType());
        messageDTO.setCreatedAt(message.getCreatedAt());

        // lay id tu doi tuong conversation
        messageDTO.setConversationId(message.getConversation().getConversationId());

        // lay id tu doi tuong sender
        messageDTO.setSenderId(message.getSender().getUserId());

        return messageDTO;
    }
}
