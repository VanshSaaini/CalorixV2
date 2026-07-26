package com.calorix.backend.controller;

import com.calorix.backend.dto.common.ApiResponse;
import com.calorix.backend.dto.macro.MacroRecordRequest;
import com.calorix.backend.dto.macro.MacroRecordResponse;
import com.calorix.backend.service.MacroRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/macros")
@RequiredArgsConstructor
public class MacroRecordController {

    private final MacroRecordService macroRecordService;

    /**
     * Save Macro Record
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<MacroRecordResponse> saveMacroRecord(
            @PathVariable Long userId,
            @Valid @RequestBody MacroRecordRequest request) {

        MacroRecordResponse response =
                macroRecordService.saveMacros(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Macro Record
     */
    @PutMapping("/{id}")
    public ResponseEntity<MacroRecordResponse> updateMacroRecord(
            @PathVariable Long id,
            @Valid @RequestBody MacroRecordRequest request) {

        return ResponseEntity.ok(
                macroRecordService.updateMacros(id, request)
        );
    }

    /**
     * Get Macro Record By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<MacroRecordResponse> getMacroRecord(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                macroRecordService.getMacros(id)
        );
    }

    /**
     * Get All Macro Records Of User
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MacroRecordResponse>> getUserMacroRecords(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                macroRecordService.getUserMacros(userId)
        );
    }

    /**
     * Get Latest Macro Record
     */
    @GetMapping("/latest/{userId}")
    public ResponseEntity<MacroRecordResponse> getLatestMacroRecord(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                macroRecordService.getLatestMacros(userId)
        );
    }

    /**
     * Delete Macro Record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMacroRecord(
            @PathVariable Long id) {

        macroRecordService.deleteMacros(id);

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Macro record deleted successfully.")
                        .build()
        );
    }
}