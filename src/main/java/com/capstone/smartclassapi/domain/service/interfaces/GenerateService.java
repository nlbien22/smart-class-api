package com.capstone.smartclassapi.domain.service.interfaces;

import com.capstone.smartclassapi.api.dto.request.GenerateRequest;
import com.capstone.smartclassapi.api.dto.response.QuestionAnswerResponse;

public interface GenerateService {
    QuestionAnswerResponse generateQuestion(GenerateRequest generateRequest);
}
