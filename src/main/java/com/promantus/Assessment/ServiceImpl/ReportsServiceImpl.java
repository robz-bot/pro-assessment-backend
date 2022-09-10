package com.promantus.Assessment.ServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.promantus.Assessment.Dto.ReportsDto;
import com.promantus.Assessment.Entity.Reports;
import com.promantus.Assessment.Entity.User;
import com.promantus.Assessment.Repository.ReportsRepository;
import com.promantus.Assessment.Repository.UserRepository;
import com.promantus.Assessment.Service.CommonService;
import com.promantus.Assessment.Service.ReportsService;

@Service
public class ReportsServiceImpl implements ReportsService {

	private static final Logger logger = LoggerFactory.getLogger(ReportsServiceImpl.class);

	@Autowired
	ReportsRepository reportsRepository;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public ReportsDto addReports( ReportsDto reportsDto, String lang) throws Exception {

		ReportsDto resultDto = new ReportsDto();
		User repUser = userRepository.findById(reportsDto.getUserId());
		//if (reportsRepository.findByUserId(reportsDto.getUserId()) == null) 
			
			Reports reports = new Reports();
			reports.setId(commonService.nextSequenceNumber());
			reports.setUserId(reportsDto.getUserId());
			reports.setPercentage(reportsDto.getPercentage());
			reports.setStatus(reportsDto.getStatus());
			reports.setTotalNoOfQuestions(reportsDto.getTotalNoOfQuestions());
			reports.setNoOfQuestionsAnswered(reportsDto.getNoOfQuestionsAnswered());
			reports.setNoOfQuestionsNotAnswered(reportsDto.getNoOfQuestionsNotAnswered());
			reports.setReportedOn(LocalDateTime.now());
			repUser.setAttempts(repUser.getAttempts()+1);
			userRepository.save(repUser);
			reportsRepository.save(reports);	
		resultDto.setMessage("Reports added successfully");
        return resultDto;
	}

		
	@Override
	public List<ReportsDto> getAllReports() {
		List<Reports> ReportssList = reportsRepository.findAll();

		List<ReportsDto> ReportsDtoList = new ArrayList<ReportsDto>();
		for (Reports Reports : ReportssList) {
			ReportsDtoList.add(this.getReportsDto(Reports));
		}

		return ReportsDtoList;
	}
	
	
	private ReportsDto getReportsDto(Reports reports) {
		ReportsDto reportsDto=new ReportsDto();

		reportsDto.setId(reports.getId());
		reportsDto.setUserId(reports.getUserId());
		reportsDto.setPercentage(reports.getPercentage());
		reportsDto.setTotalNoOfQuestions(reports.getTotalNoOfQuestions());
		reportsDto.setNoOfQuestionsAnswered(reports.getNoOfQuestionsAnswered());
		reportsDto.setNoOfQuestionsNotAnswered(reports.getNoOfQuestionsNotAnswered());
		reportsDto.setStatus(reports.getStatus());
		reportsDto.setReportedOn(reports.getReportedOn());
		
		return reportsDto;
		
	}
	@Override
	public ReportsDto updateReports(ReportsDto reportsDto, String lang) {

		ReportsDto resultDto = new ReportsDto();
		System.out.println(reportsDto.getId());
		Reports reports = reportsRepository.findById(reportsDto.getId());
		if (reports == null) {
			resultDto.setMessage("Reports does not exist");
			return resultDto;
		}
		reports.setUserId(reportsDto.getUserId());
		reports.setPercentage(reportsDto.getPercentage());
		reports.setTotalNoOfQuestions(reportsDto.getTotalNoOfQuestions());
		reports.setNoOfQuestionsAnswered(reportsDto.getNoOfQuestionsAnswered());
		reports.setNoOfQuestionsNotAnswered(reportsDto.getNoOfQuestionsNotAnswered());
		reports.setStatus(reportsDto.getStatus());
		reports.setReportedOn(reportsDto.getReportedOn());
		reports.setUpdatedOn(LocalDateTime.now());
		reportsRepository.save(reports);
		resultDto.setMessage("Record Updated successfully");
		return resultDto;
       
	}

	@Override
	public ReportsDto deleteReportsById(String id) {
		ReportsDto resultDto=new ReportsDto();
		Reports reports=reportsRepository.findById(Long.parseLong(id));
		if(reports==null)
		{
			resultDto.setMessage("data does not exist");
			return resultDto;
		}
		reportsRepository.delete(reports);
		resultDto.setMessage("Record Deleted successfully");
		return resultDto;
	}

	@Override
	public ReportsDto getReportsById(String id) throws Exception {
			Reports reports = reportsRepository.findById(Long.parseLong(id));
			return reports != null ? this.getReportsDto(reports) : new ReportsDto();
	}


}
