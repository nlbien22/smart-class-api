package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.request.CloRequest;
import com.capstone.smartclassapi.api.dto.response.AllCloResponse;
import com.capstone.smartclassapi.api.dto.response.CloResponse;
import com.capstone.smartclassapi.domain.entity.CloEntity;
import com.capstone.smartclassapi.domain.entity.SubjectEntity;
import com.capstone.smartclassapi.domain.exception.ResourceConflictException;
import com.capstone.smartclassapi.domain.exception.ResourceNotFoundException;
import com.capstone.smartclassapi.domain.repository.CloRepository;
import com.capstone.smartclassapi.domain.service.interfaces.CloService;
import com.capstone.smartclassapi.domain.service.interfaces.SubjectService;
import com.capstone.smartclassapi.domain.sort.Sorting;
import com.capstone.smartclassapi.domain.validations.CommonValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CloServiceImp implements CloService {
    private final CloRepository cloRepository;
    private final SubjectService subjectService;
    @Override
    public CloEntity getClo(Long subjectId, Long CloId) {
        return cloRepository.findCloById(subjectId, CloId)
                .orElseThrow(() -> new ResourceNotFoundException("Clo not found"));
    }

    @Override
    public CloEntity getCloById(Long CloId) {
        return cloRepository.findById(CloId)
                .orElseThrow(() -> new ResourceNotFoundException("Clo not found"));
    }

    @Override
    public AllCloResponse getAllClos(Long subjectId, int page, int size, String keyword, String sortType, String sortValue) {
        CommonValidation.validatePageAndSize(page, size);
        Sort sorting = Sorting.getSorting(sortType, sortValue);
        Pageable pageable = PageRequest.of(page, size, sorting);

        List<CloEntity> Clos = cloRepository.findAllClos(
                subjectId,
                CommonValidation.escapeSpecialCharacters(keyword.trim()),
                pageable
        );
        return AllCloResponse.builder()
                .clos(Clos.stream().map(CloResponse::fromEntity).toList())
                .totalClos(cloRepository.countBySubjectId(
                        subjectId,
                        CommonValidation.escapeSpecialCharacters(keyword.trim()))
                )
                .build();
    }

    @Override
    public void createClo(Long subjectId, CloRequest request) {
        cloRepository.findCloByName(subjectId, request.getCloTitle())
                .ifPresent(cloEntity -> {
                    throw new ResourceConflictException(String.format("Clo %s already exists", request.getCloTitle()));
                });
        SubjectEntity subjectEntity = subjectService.getSubject(subjectId);
        CloEntity cloEntity = CloEntity.builder()
                .cloTitle(request.getCloTitle())
                .subject(subjectEntity)
                .build();

        cloRepository.save(cloEntity);
    }

    @Override
    public void updateClo(Long subjectId, Long CloId, CloRequest request) {
        CloEntity cloEntity = cloRepository.findCloById(subjectId, CloId)
                .orElseThrow(() -> new ResourceNotFoundException("Clo not found"));

        Optional<CloEntity> CloEntityOptional = cloRepository.findCloByName(subjectId, request.getCloTitle());
        if (CloEntityOptional.isPresent() && !CloEntityOptional.get().getCloId().equals(CloId)) {
            throw new ResourceConflictException(String.format("Clo %s already exists", request.getCloTitle()));
        }

        cloEntity.setCloTitle(request.getCloTitle());
        cloRepository.save(cloEntity);
    }

    @Override
    public void deleteClo(Long subjectId, Long CloId) {
        CloEntity cloEntity = cloRepository.findCloById(subjectId, CloId)
                .orElseThrow(() -> new ResourceNotFoundException("Clo not found"));
        cloRepository.delete(cloEntity);
    }
}
