package org.example.controller;

import org.example.dto.ReportDTO;
import org.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController (ReportService reportService){
        this.reportService = reportService;
    }

    // tao mot bao cao
    @PostMapping
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO){
        ReportDTO createdReport = reportService.createReport(reportDTO);
        return ResponseEntity.ok(createdReport);
    }

    // lay dnah sach bao cao theo trang thai
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReportDTO>> getReportsByStatus(@PathVariable("status") String status){
        List<ReportDTO> reports = reportService.getReportsByStatus(status);
        return ResponseEntity.ok(reports);
    }

    // lay danh sach bao cao theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReportDTO>> getReportsByUserId(@PathVariable("userId") Long userId){
        List<ReportDTO> reports = reportService.getReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }

    // lay danh sach bao cao theo participantId
    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<ReportDTO>> getReportsByParticipantId(@PathVariable("participantId") Long participantId){
        List<ReportDTO> reports = reportService.getReportsByParticipantId(participantId);
        return ResponseEntity.ok(reports);
    }

    //lay danh sach theo loai bao cao
    @GetMapping("/type/{reportType}")
    public ResponseEntity<List<ReportDTO>> getReportsByReportType(@PathVariable("reportType") String reportType){
        List<ReportDTO> reports = reportService.getReportsByReportType(reportType);
        return ResponseEntity.ok(reports);
    }

    //lay danh sach theo userId va status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<ReportDTO>> getReportsByUserIdAndStatus(@PathVariable("userId") Long userId, @PathVariable("status") String status){
        List<ReportDTO> reports = reportService.getReportsByUserIdAndStatus(userId, status);
        return ResponseEntity.ok(reports);
    }

    // lay danh sach bao cao theo participantId va status
    @GetMapping("/participant/{participantId}/status/{status}")
    public ResponseEntity<List<ReportDTO>> getReportsByParticipantIdAndStatus(@PathVariable("participantId") Long participantId, @PathVariable("status") String status){
        List<ReportDTO> reports = reportService.getReportsByParticipantIdAndStatus(participantId, status);
        return ResponseEntity.ok(reports);
    }

    // thay doi trang thai bao cao
    @GetMapping("/{reportId}/status")
    public ResponseEntity<ReportDTO> changeReportStatus(@PathVariable Long reportId, String status){
        ReportDTO updatedReport = reportService.changeReportStatus(reportId, status);
        return ResponseEntity.ok(updatedReport);
    }

}
