package com.capstone.smartclassapi.domain.service;

import com.capstone.smartclassapi.api.dto.response.AllGradedResponse;
import com.capstone.smartclassapi.api.dto.response.AllStudentResponse;
import com.capstone.smartclassapi.api.dto.response.GradedResponse;
import com.capstone.smartclassapi.domain.entity.GradedEntity;
import com.capstone.smartclassapi.domain.entity.KeyEntity;
import com.capstone.smartclassapi.domain.entity.ResultEntity;
import com.capstone.smartclassapi.domain.repository.GradedRepository;
import com.capstone.smartclassapi.domain.service.interfaces.GradedService;
import com.capstone.smartclassapi.domain.service.interfaces.KeyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GradedServiceImp implements GradedService {
    private final GradedRepository gradedRepository;
    private final KeyService keyService;

//    @Transactional
    @Override
    public void saveGraded(Long examId, GradedResponse request) {
        KeyEntity keyEntity = keyService.findByKeyCode(examId, request.getKeyCode());
        GradedEntity gradedEntityInDb = gradedRepository
                .findByExamIdAndStudentId(examId, request.getUserCode());
        if (gradedEntityInDb != null)
            gradedRepository.delete(gradedEntityInDb);

        GradedEntity gradedEntity = GradedEntity.builder()
                .key(keyEntity)
                .keyCode(request.getKeyCode())
                .score(request.getScore())
                .total(request.getTotal())
                .imageKey(request.getImageKey())
                .nameKey(request.getNameKey())
                .isError(request.getIsError())
                .correct(request.getCorrect())
                .userCode(request.getUserCode())
                .className(request.getClassName())
                .studentName(request.getStudentName())
                .gradedDate(request.getUpdatedAt())
                .results(new ArrayList<>())
                .build();

        List<String> answers = convertAnswerToAlphabet(request.getAnswers());
        for (int i = 0; i < request.getAnswers().size(); i++) {
            ResultEntity resultEntity = ResultEntity.builder()
                    .graded(gradedEntity)
                    .questionIndex(i + 1)
                    .choice(answers.get(i))
                    .build();
            gradedEntity.getResults().add(resultEntity);
        }
        if (keyEntity != null && keyEntity.getGradedEntities() == null)
            keyEntity.setGradedEntities(new ArrayList<>());
        gradedRepository.save(gradedEntity);
    }

    @Override
    public void updateGraded(Long examId, Long gradedId, GradedResponse request) {

    }

    @Override
    public void deleteGraded(Long examId, Long gradedId) {
        GradedEntity gradedEntity = gradedRepository.findById(gradedId)
                .orElseThrow(() -> new IllegalArgumentException("Graded not found"));
        gradedRepository.delete(gradedEntity);
    }

    @Override
    public void deleteAllGraded(Long examId) {

    }

    @Override
    public GradedResponse getGraded(Long examId, Long gradedId) {
        GradedEntity gradedEntity = gradedRepository.findById(gradedId)
                .orElseThrow(() -> new IllegalArgumentException("Graded not found"));
        return GradedResponse.builder()
                .gradedId(gradedEntity.getGradedId())
                .examId(examId)
                .keyCode(gradedEntity.getKeyCode())
                .score(gradedEntity.getScore())
                .total(gradedEntity.getTotal())
                .imageKey(String.valueOf(gradedEntity.getImageKey()))
                .nameKey(String.valueOf(gradedEntity.getNameKey()))
                .isError(gradedEntity.getIsError())
                .correct(gradedEntity.getCorrect())
                .userCode(gradedEntity.getUserCode())
                .className(gradedEntity.getClassName())
                .studentName(gradedEntity.getStudentName())
                .updatedAt(gradedEntity.getGradedDate())
                .answers(gradedEntity.getResults().stream()
                        .map(resultEntity -> (int) resultEntity.getChoice().charAt(0) - 65)
                        .toList())
                .build();
    }

    @Override
    public AllGradedResponse getAllGrades(Long examId, int page, int size, String keyword, String sortType, String sortValue) {
        List<GradedEntity> gradedEntities = gradedRepository.findByExamId(examId);
        List<GradedResponse> gradedResponses = new ArrayList<>();
        for (GradedEntity gradedEntity : gradedEntities) {
            gradedResponses.add(GradedResponse.builder()
                    .gradedId(gradedEntity.getGradedId())
                    .examId(examId)
                    .keyCode(gradedEntity.getKeyCode())
                    .score(gradedEntity.getScore())
                    .total(gradedEntity.getTotal())
                    .imageKey(String.valueOf(gradedEntity.getImageKey()))
                    .nameKey(String.valueOf(gradedEntity.getNameKey()))
                    .isError(gradedEntity.getIsError())
                    .correct(gradedEntity.getCorrect())
                    .userCode(gradedEntity.getUserCode())
                    .className(gradedEntity.getClassName())
                    .studentName(gradedEntity.getStudentName())
                    .updatedAt(gradedEntity.getGradedDate())
                    .answers(gradedEntity.getResults().stream()
                            .map(resultEntity -> (int) resultEntity.getChoice().charAt(0) - 65)
                            .toList())
                    .build());
        }
        return AllGradedResponse.builder()
                .listGrades(gradedResponses)
                .build();
    }

    List<String> convertAnswerToAlphabet(List<Integer> answers) {
        return answers.stream()
                .map(answer -> String.valueOf((char) (answer + 65)))
                .toList();
    }

}
