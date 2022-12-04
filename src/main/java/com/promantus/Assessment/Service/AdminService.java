package com.promantus.Assessment.Service;

import java.util.Map;

import com.promantus.Assessment.Dto.AdminRequestDto;

public interface AdminService {

	Map<Object, Object> addAdminRequest(AdminRequestDto generalQuestionDto) throws Exception;

	Map<Object, Object> adminApproveOrDecline(AdminRequestDto adminRequestDto, String req) throws Exception;

	Map<Object, Object> getAllAdminRequest() throws Exception;

	Map<Object, Object> getAdminReqDet(AdminRequestDto adminRequestDto)throws Exception;

}
