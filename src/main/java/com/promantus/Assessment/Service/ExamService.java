package com.promantus.Assessment.Service;

import java.util.List;

import com.promantus.Assessment.Dto.ExamDto;

public interface ExamService {


	List<ExamDto> getExamQns(String teamId, String userId)throws Exception;

}
