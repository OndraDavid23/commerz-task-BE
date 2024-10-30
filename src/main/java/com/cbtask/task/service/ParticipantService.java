package com.cbtask.task.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cbtask.task.dto.ParticipantDTO;
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
    public ParticipantDTO getParticipantById(Long id) {
        return createDTO(participantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Participant not found")));
    }

    @Override
    public List<ParticipantDTO> getAllParticipants() {
        List<Participant> participants = participantRepository.findAll();
        return participants.stream().map(this::createDTO).toList();
    }

    @Override
    public Page<ParticipantDTO> getPaginatedParticipants(int pageNumber, int pageSize) {
        Page<Participant> participants = participantRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id")));
        return participants.map(this::createDTO);
    }

    @Override
    public ParticipantDTO addParticipant(CreateParticipantRequest request) {
        Participant participant = new Participant(); 
        participant.setFirstName(request.getFirstName());
        participant.setLastName(request.getLastName());
        participant.setAge(request.getAge());
        participant = participantRepository.save(participant);

        return createDTO(participant);
    }

    @Override
    public List<ParticipantDTO> createTestParticipants(List<CreateParticipantRequest> request) {
        return request.stream().map(this::addParticipant).toList();

    }

    private ParticipantDTO createDTO(Participant participant){
        ParticipantDTO participantDTO = new ParticipantDTO(
            participant.getId(),
            participant.getFirstName(),
            participant.getLastName(),
            participant.getAge());

        return  participantDTO;
    } 
}
 