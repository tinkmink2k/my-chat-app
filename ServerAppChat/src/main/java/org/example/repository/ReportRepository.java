package org.example.repository;

import org.example.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    // custom query to find reports by status
    List<Report> findByStatus(String status);

    // custiom query to find report by userId
    List<Report> findByUser_UserId(Long userId);

    //custom query to find report bby participantId
    List<Report> findByParticipant_ParticipantId(Long participant);

    //custom query to find report by reportType
    List<Report> findByReportType(String reportType);

    // Custom query to find reports by both userId and status
    List<Report> findByUser_UserIdAndStatus(Long userId, String status);

    // Custom query to find reports by both participantId and status
    List<Report> findByParticipant_ParticipantIdAndStatus(Long participantId, String status);
}
