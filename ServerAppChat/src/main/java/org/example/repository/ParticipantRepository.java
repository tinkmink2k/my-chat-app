package org.example.repository;

import org.example.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByConversation_ConversationId(Long ConversationId);
    List<Participant> findByUser_UserId(Long UserId);
}
