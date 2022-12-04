/**
 * 
 */
package com.promantus.Assessment.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promantus.Assessment.AssessmentConstants;
import com.promantus.Assessment.AssessmentUtil;
import com.promantus.Assessment.Dto.AdminRequestDto;
import com.promantus.Assessment.Dto.GeneralQuestionDto;
import com.promantus.Assessment.Service.AdminService;

/**
 * @author Promantus
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class AdminController {

	@Autowired
	AdminService adminService;

	@PostMapping("/addAdminRequest")
	public Map<Object, Object> addAdminRequest(@RequestBody AdminRequestDto adminRequestDto) {
		try {

			return adminService.addAdminRequest(adminRequestDto);

		} catch (final Exception e) {
			System.err.println("Err from addAdminRequest");
			System.err.println(AssessmentConstants.RETURN_STATUS_ERROR);
			System.err.println(e.getMessage());
		}

		return new HashMap<Object, Object>();
	}
	
	
	
	@PostMapping("/adminApproveOrDecline/{req}")
	public Map<Object, Object> adminApproveOrDecline(@RequestBody AdminRequestDto adminRequestDto, @PathVariable String req) {
		
		try {

			return adminService.adminApproveOrDecline(adminRequestDto,req);

		} catch (final Exception e) {
			System.err.println("Err from adminApproveOrDecline");
			System.err.println(AssessmentConstants.RETURN_STATUS_ERROR);
			System.err.println(e.getMessage());
		}

		return new HashMap<Object, Object>();
	}
	
	@PostMapping("/getAdminReqDet")
	public Map<Object,Object> getAdminReqDet(@RequestBody AdminRequestDto adminRequestDto){
		try {

			return adminService.getAdminReqDet(adminRequestDto);

		} catch (final Exception e) {
			System.err.println("Err from adminApproveOrDecline");
			System.err.println(AssessmentConstants.RETURN_STATUS_ERROR);
			System.err.println(e.getMessage());
		}

		return new HashMap<Object, Object>();
	}
	
	@GetMapping("/getAllAdminRequest")
	public Map<Object,Object> getAllAdminRequest(){
		try {

			return adminService.getAllAdminRequest();

		} catch (final Exception e) {
			System.err.println("Err from adminApproveOrDecline");
			System.err.println(AssessmentConstants.RETURN_STATUS_ERROR);
			System.err.println(e.getMessage());
		}

		return new HashMap<Object, Object>();
	}
}
