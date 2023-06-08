package com.promantus.Assessment.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentDefaultMethods;
import com.promantus.Assessment.SmtpMailSender;
import com.promantus.Assessment.Dto.AdminRequestDto;
import com.promantus.Assessment.Entity.AdminRequest;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Repository.AdminRequestRepository;
import com.promantus.Assessment.Repository.TeamRepository;
import com.promantus.Assessment.Service.AdminService;
import com.promantus.Assessment.Service.CommonService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRequestRepository adminReqRepo;

	@Autowired
	CommonService commonService;

	@Autowired
	SmtpMailSender smtpMailSender;

	@Autowired
	TeamRepository teamRepo;

	@Override
	public Map<Object, Object> addAdminRequest(AdminRequestDto adminRequestDto) throws Exception {

		Map<Object, Object> response = new HashMap<Object, Object>();

		String todayDate = LocalDateTime.now().toString().split("T")[0];
		AdminRequest reqDate = adminReqRepo.findByReqRaisedOn(adminRequestDto.getReqRaisedOn());
		AdminRequest req = adminReqRepo.findByEmail(adminRequestDto.getEmail());

		if (req != null && req.getReqRaisedOn().toString().split("T")[0].equals(todayDate)) {
			response.put("status", 1);
			response.put("message", "Request already raised today!");
		}

		if (req != null) {
			response.put("status", 1);
			response.put("message", "Request already raised for this email!");
		}

		if (req == null) {
			AdminRequest adminRequest = new AdminRequest();
			adminRequest.setId(commonService.nextSequenceNumber());
			adminRequest.setTeamId(adminRequestDto.getTeamId());
			adminRequest.setEmail(adminRequestDto.getEmail());
			adminRequest.setReason(adminRequestDto.getReason());
			adminRequest.setPassword(AssessmentDefaultMethods.randomNumber());
			adminRequest.setApprove(false);
			adminRequest.setReqRaisedOn(LocalDateTime.now());

			adminReqRepo.save(adminRequest);

			response.put("status", 0);
			response.put("message",
					"Your request was successfully raised. Once it has been accepted or rejected by the superadmin, you will be notified via mail.");
		}
		return response;
	}

	@Override
	public Map<Object, Object> adminApproveOrDecline(AdminRequestDto adminRequestDto, String req) throws Exception {
		Map<Object, Object> response = new HashMap<Object, Object>();

		response.put("status", 0);
		AdminRequest adminRequest = adminReqRepo.findById(adminRequestDto.getId());
		if (req.equals(AssessmentConstants.APPROVE)) {
			adminRequest.setApprove(true);

			response.put("message", "Request Approved");
			response.put("icon", "success");
		}
		if (req.equals(AssessmentConstants.DECLINE)) {
			adminRequest.setApprove(false);
			response.put("message", "Request Declined");
			response.put("icon", "error");
		}

		adminRequest.setReqApproveOrDeclineOn(LocalDateTime.now());

		adminReqRepo.save(adminRequest);

		// Mail Thread
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					AdminRequest adminReq = adminReqRepo.findById(adminRequestDto.getId());
					Team team = teamRepo.findById(adminReq.getTeamId());
					smtpMailSender.sendApproveOrDeclineMail(adminReq, team.getTeam());
				} catch (Exception e) {

					System.out.println("Admin Approve/Decline mail is not Sent.");
					System.err.println(e);
				}
			}
		}).start();

		return response;
	}

	public Map<Object, Object> getAllAdminRequest() throws Exception {
		Map<Object, Object> response = new HashMap<Object, Object>();

		response.put("status", 0);
		List<AdminRequest> adminRequestList = adminReqRepo.findAll();

		List<AdminRequestDto> adminRequestDtoList = new ArrayList<AdminRequestDto>();
		for (AdminRequest item : adminRequestList) {
			adminRequestDtoList.add(this.getAdminRequest(item));
		}

		response.put("adminRequestList", adminRequestDtoList);
		response.put("count", adminRequestDtoList.size());

		return response;
	}

	private AdminRequestDto getAdminRequest(AdminRequest req) {
		AdminRequestDto adminRequestDto = new AdminRequestDto();

		adminRequestDto.setId(req.getId());
		adminRequestDto.setTeamId(req.getTeamId());
		adminRequestDto.setPassword(req.getPassword());
		adminRequestDto.setReason(req.getReason());
		adminRequestDto.setReqRaisedOn(req.getReqRaisedOn());
		adminRequestDto.setReqApproveOrDeclineOn(req.getReqApproveOrDeclineOn());
		adminRequestDto.setEmail(req.getEmail());
		adminRequestDto.setTeam(teamRepo.findById(req.getTeamId()).getTeam());
		adminRequestDto.setApprove(req.isApprove());
		return adminRequestDto;

	}

	@Override
	public Map<Object, Object> getAdminReqDet(AdminRequestDto adminRequestDto) throws Exception {

		Map<Object, Object> response = new HashMap<Object, Object>();

		AdminRequest adminReq = adminReqRepo.findByEmail(adminRequestDto.getEmail());

		if (adminReq != null && adminReq.isApprove()) {
			response.put("status", "0");
			response.put("message", "LoggedIn Success!");
			response.put("res", adminReq);
		}else {
			response.put("status", "1");
			response.put("message", "Make sure you have raised request for Admin! Or Check your credentials");
		}

		return response;
	}

}
