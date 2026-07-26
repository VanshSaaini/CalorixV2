package com.calorix.backend.service;

import com.calorix.backend.dto.macro.MacroRecordRequest;
import com.calorix.backend.dto.macro.MacroRecordResponse;

import java.util.List;

public interface MacroRecordService {

    MacroRecordResponse saveMacros(Long userId, MacroRecordRequest request);

    MacroRecordResponse updateMacros(Long id, MacroRecordRequest request);

    MacroRecordResponse getMacros(Long id);

    List<MacroRecordResponse> getUserMacros(Long userId);

    MacroRecordResponse getLatestMacros(Long userId);

    void deleteMacros(Long id);
}