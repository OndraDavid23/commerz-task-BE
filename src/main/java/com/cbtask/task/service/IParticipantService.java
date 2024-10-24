package com.cbtask.task.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cbtask.task.model.Participant;
import com.cbtask.task.request.CreateParticipantRequest;

public interface IParticipantService {
    Participant getParticipantById(Long id);
    List<Participant> getAllParticipants();
    Participant addParticipant(CreateParticipantRequest request);
    Page<Participant> getPaginatedParticipants(int pageNumber, int pageSize);
    List<Participant> createTestParticipants(List<CreateParticipantRequest> request);


}
