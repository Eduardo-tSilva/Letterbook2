package br.com.Letterbook.Letterbook.service;

import br.com.Letterbook.Letterbook.model.Assessment;
import br.com.Letterbook.Letterbook.model.DTO.AssessmentDTO;
import br.com.Letterbook.Letterbook.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentService {
    @Autowired
    private UsersRepository usersRepository;

    public AssessmentDTO toDto(Assessment assessment) {
        AssessmentDTO dto = new AssessmentDTO(assessment);
        // Carrega o nome do usuário
        usersRepository.findById(assessment.getUsuarioId())
                .ifPresent(user -> dto.setUserName(user.getNome()));
        return dto;
    }

}
