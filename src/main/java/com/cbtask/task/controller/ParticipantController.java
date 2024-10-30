package com.cbtask.task.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbtask.task.dto.ParticipantDTO;
import com.cbtask.task.request.CreateParticipantRequest;
import com.cbtask.task.response.ApiResponse;
import com.cbtask.task.service.IParticipantService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/participants")
@CrossOrigin("*")
public class ParticipantController {
    private final IParticipantService participantService;


    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllParticipants() {
        try {
            List<ParticipantDTO> participants = participantService.getAllParticipants();
            return ResponseEntity.ok().body(new ApiResponse("Participants found", participants));
        } catch (RuntimeException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/getPaginated")
    public ResponseEntity<ApiResponse> getPaginatedParticipants(@RequestParam(defaultValue="0") int pageNumber, @RequestParam(defaultValue="10") int pageSize) {
        try {
            Page<ParticipantDTO> paginatedParticipants = participantService.getPaginatedParticipants(pageNumber, pageSize);
            return ResponseEntity.ok().body(new ApiResponse("Participants found", paginatedParticipants));
        } catch (RuntimeException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getParticipantById(@PathVariable Long id) {
        try {
            ParticipantDTO participant = participantService.getParticipantById(id);
            return ResponseEntity.ok().body(new ApiResponse("Participants found", participant));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Error", e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createParticipant(@Valid @RequestBody CreateParticipantRequest request) {
        try {
            ParticipantDTO participant = participantService.addParticipant(request);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Participants created", participant));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/createTestData")
    public ResponseEntity<ApiResponse> createTestParticipants(@RequestBody List<@Valid CreateParticipantRequest> request) {
        try {
            List<ParticipantDTO> participants = participantService.createTestParticipants(request);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Participants created", participants));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }   
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(BAD_REQUEST).body(new ApiResponse("Error creating new participant.",errors));
    }
}
