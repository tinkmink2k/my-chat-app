package org.example.repository;

import org.example.model.Message;
import org.example.model.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    //tim kiem cuoc hoi thoai theo conversation_id
    List<Message> findByConversation_ConversationId(Long conversationId);

    List<Message> findBySender_UserId(Long senderId);

    List<Message> findByConversation_ConversationIdAndMessageType(Long conversationId, MessageType messageType);
}
