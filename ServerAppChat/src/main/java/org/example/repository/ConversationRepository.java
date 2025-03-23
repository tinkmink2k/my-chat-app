package org.example.repository;

import org.example.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    // tim kiem danh sach cuoc tro chuyen theo creatorId
    List<Conversation> findByCreator_UserId(Long creatorId);

    // tim kiem cuoc tro chuyen theoi title
    List<Conversation> findByTitleContaining(String title);

    // tim kiem theo channelId
    Optional<Conversation> findByChannelId(String channelId);

    // tim kiem cuoc tro chuyen theo title va creatorId
    List<Conversation> findByTitleContainingAndCreator_UserId(String title, Long creatorId);

    // tim kiem cuoc tro chuyen theo title, creatorId, channelId
    List<Conversation> findByTitleAndCreatorIdAndChannelId(String tile, Long creatorId, String channelId);
}
