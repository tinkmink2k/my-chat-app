package org.example.service;

import org.example.dto.ReportDTO;
import org.example.model.Participant;
import org.example.model.Report;
import org.example.model.ReportStatus;
import org.example.model.User;
import org.example.repository.ParticipantRepository;
import org.example.repository.ReportRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository, ParticipantRepository participantRepository){
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
    }

    // Lấy danh sách báo cáo theo trạng thái
    public List<ReportDTO> getReportsByStatus(String status) {
        List<Report> reports = reportRepository.findByStatus(status);
        return reports.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    // Lấy danh sách báo cáo theo userId
    public List<ReportDTO> getReportsByUserId(Long userId) {
        List<Report> reports = reportRepository.findByUser_UserId(userId);
        return reports.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    // Lấy danh sách báo cáo theo participantId
    public List<ReportDTO> getReportsByParticipantId(Long participantId) {
        List<Report> reports = reportRepository.findByParticipant_ParticipantId(participantId);
        return reports.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    // Lấy danh sách báo cáo theo loại báo cáo
    public List<ReportDTO> getReportsByReportType(String reportType) {
        List<Report> reports = reportRepository.findByReportType(reportType);
        return reports.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    // Lấy danh sách báo cáo theo userId và trạng thái
    public List<ReportDTO> getReportsByUserIdAndStatus(Long userId, String status) {
        List<Report> reports = reportRepository.findByUser_UserIdAndStatus(userId, status);
        return reports.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    // Lấy danh sách báo cáo theo participantId và trạng thái
    public List<ReportDTO> getReportsByParticipantIdAndStatus(Long participantId, String status) {
        List<Report> reports = reportRepository.findByParticipant_ParticipantIdAndStatus(participantId, status);
        return reports.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    // create new report
    public ReportDTO createReport(ReportDTO reportDTO){
        Report report = convertDTOToEntity(reportDTO);
        report = reportRepository.save(report);
        return convertEntityToDTO(report);
    }

    // get a report by id
    public ReportDTO getReportById(Long reportId){
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id:" + reportId));
        return convertEntityToDTO(report);
    }

    // get all report
    public List<ReportDTO> getAllReports(){
        return reportRepository.findAll().stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    // update a report by id
    public ReportDTO updateReport(Long reportId, ReportDTO reportDTO){
        Report existingReport = reportRepository.findById(reportId)
                .orElseThrow(()-> new RuntimeException("Report not found with id" + reportId));

        existingReport.setReportType(reportDTO.getReportType());
        existingReport.setNotes(reportDTO.getNotes());

        //update the status if provided
        if (reportDTO.getStatus() != null){
            existingReport.setStatus(ReportStatus.valueOf(reportDTO.getStatus()));
        }

        existingReport.setUpdatedAt(LocalDateTime.now());
        Report updatedReport = reportRepository.save(existingReport);
        return convertEntityToDTO(updatedReport);
    }

    // delete a report by id
    public void deleteReport(Long reportId){
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id:" + reportId));
        reportRepository.delete(report);
    }

    // chande the status of the report( pending -> resolved )
    public ReportDTO changeReportStatus(Long reportId, String newStatus){
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id:" + reportId));

        report.setStatus(ReportStatus.valueOf(newStatus));
        report.setUpdatedAt(LocalDateTime.now());

        Report updatedReport = reportRepository.save(report);
        return convertEntityToDTO(updatedReport);
    }

    private Report convertDTOToEntity(ReportDTO reportDTO){
        Report report = new Report();
        report.setReportId(reportDTO.getReportId());
        report.setReportType(reportDTO.getReportType());
        report.setNotes(reportDTO.getNotes());
        report.setStatus(ReportStatus.valueOf(reportDTO.getStatus()));
        report.setUser(fetchUserById(reportDTO.getUserId()));
        report.setParticipant(fetchParticipantById(reportDTO.getParticipantId()));
        return report;
    }

    private Participant fetchParticipantById(Long participantId) {
        return participantRepository.findById(participantId)
                .orElseThrow(() -> new RuntimeException("Participant not found with id:" + participantId));
    }

    private User fetchUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found with id:" + userId));
    }

    private ReportDTO convertEntityToDTO(Report report){
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setReportId(report.getReportId());
        reportDTO.setReportType(report.getReportType());
        reportDTO.setNotes(report.getNotes());
        reportDTO.setStatus(report.getStatus().name());
        reportDTO.setUserId(report.getUser().getUserId());
        reportDTO.setParticipantId(report.getParticipant().getParticipantId());
        reportDTO.setCreatedAt(report.getCreatedAt());
        return reportDTO;
    }
}
