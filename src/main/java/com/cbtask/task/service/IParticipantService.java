package com.cbtask.task.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cbtask.task.dto.ParticipantDTO;
import com.cbtask.task.request.CreateParticipantRequest;

public interface IParticipantService {
    ParticipantDTO getParticipantById(Long id);
    List<ParticipantDTO> getAllParticipants();
    ParticipantDTO addParticipant(CreateParticipantRequest request);
    Page<ParticipantDTO> getPaginatedParticipants(int pageNumber, int pageSize);
    List<ParticipantDTO> createTestParticipants(List<CreateParticipantRequest> request);


}
