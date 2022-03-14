package com.eventus.backend.services;

import com.eventus.backend.models.Participation;
import com.eventus.backend.repositories.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ParticipationService {

    private ParticipationRepository partRepository;

    @Autowired
    public ParticipationService(ParticipationRepository partRepository) {
       this.partRepository = partRepository;
    }

    @Transactional
    public void saveParticipation(Participation participation) throws DataAccessException {
        partRepository.save(participation);
    }

    public Page<Participation> findAllParticipation(Pageable p) {
        return partRepository.findAll(p);
    }

    public Optional<Participation> findParticipationById(Long id) {
        return partRepository.findById(id);
    }

    public void deleteParticipation(Long id) {
        partRepository.deleteById(id);
    }
}
