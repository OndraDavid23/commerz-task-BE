package com.cbtask.task.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cbtask.task.exception.ResourceNotFoundException;
import com.cbtask.task.model.Participant;
import com.cbtask.task.repository.ParticipantRepository;
import com.cbtask.task.request.CreateParticipantRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantService implements IParticipantService {
    private final ParticipantRepository participantRepository;

    @Override
    public Participant getParticipantById(Long id) {
        return participantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Participant not found"));
    }

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Override
    public Page<Participant> getPaginatedParticipants(int pageNumber, int pageSize) {
        return participantRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public Participant addParticipant(CreateParticipantRequest request) {
        Participant participant = new Participant(); 
        participant.setFirstName(request.getFirstName());
        participant.setLastName(request.getLastName());
        participant.setAge(request.getAge());
        return participantRepository.save(participant);
    }

    @Override
    public List<Participant> createTestParticipants(List<CreateParticipantRequest> request) {
        List<Participant> participants = request.stream().map(this::addParticipant).toList();
        return  participants;
    }
}
