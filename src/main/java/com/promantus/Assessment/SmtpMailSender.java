package com.promantus.Assessment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.promantus.Assessment.Dto.ReportsDto;
import com.promantus.Assessment.Dto.TeamDto;
import com.promantus.Assessment.Dto.UserDto;
import com.promantus.Assessment.Entity.AdminRequest;
import com.promantus.Assessment.Entity.Team;
import com.promantus.Assessment.Entity.User;

@Component
@Configuration
@PropertySource(value = { "classpath:mail.properties" })
public class SmtpMailSender {

	@Value("${spring.mail.username}")
	private String from;

//	@Value("${ui.server.adminurl}")
//	private String adminurl;

	@Value("${bcc.email.ids}")
	private String bccEmailIds;
	
	@Value("${cc.email.ids}")
	private String ccEmailIds;

	@Autowired
	private Environment env;

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(Team team) throws AddressException, MessagingException, IOException  {

		String content = env.getProperty("team.content");
		content = content.replace("[team]", team.getTeam());
		content = content.replace("[CreatedOn]", team.getCreatedOn().toString().split("T")[0]);
//		content = content.replace("[url]", adminurl);

		String subject = env.getProperty("team.subject");
		subject = subject.replace("[team]", team.getTeam());

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);// true indicates multipart message

		helper.setFrom(from); // <--- THIS IS IMPORTANT

		helper.setSubject(subject);
		helper.setTo("robinrajesh@promantus.com");
		helper.setCc(ccEmailIds);
		helper.setText(content, true);// true indicates body is html
		javaMailSender.send(message);
	}

	public void sendUserMail(UserDto user) throws MessagingException {

		String content = env.getProperty("user.content");
		content = content.replace("[username]", user.getFirstName() + " " + user.getLastName());
		content = content.replace("[FirstName]", user.getFirstName());
		content = content.replace("[Last Name]", user.getLastName());
		content = content.replace("[EmpCode]", user.getEmpCode());
		content = content.replace("[Email]", user.getEmail());
		content = content.replace("[Manager]", user.getManager());
		content = content.replace("[Team]", user.getTeam());
		content = content.replace("[RegisteredOn]", user.getRegisteredOn().toString().split("T")[0]);

		String subject = env.getProperty("user.subject");
		subject = subject.replace("[team]", user.getTeam());

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);// true indicates multipart message

		helper.setFrom(from); // <--- THIS IS IMPORTANT

		helper.setSubject(subject);
		helper.setTo(user.getEmail());
		helper.setCc(ccEmailIds);
		helper.setText(content, true);// true indicates body is html
		javaMailSender.send(message);
	}
	
	public void sendUserResMail(ReportsDto reportsDto, User user, Team team) throws MessagingException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime now = LocalDateTime.now();

		String content = env.getProperty("user.result.content");
		content = content.replace("[username]", user.getFirstName() + " " + user.getLastName());
		content = content.replace("[EmpCode]", user.getEmpCode());
		content = content.replace("[Email]", user.getEmail());
		content = content.replace("[TotalMark]", reportsDto.getTotalMarks());
		
		content = content.replace("[NoOfQnsAns]", reportsDto.getNoOfQuestionsAnswered());
		content = content.replace("[NoOfQnsUnAns]", reportsDto.getNoOfQuestionsNotAnswered());
		content = content.replace("[Percentage]", reportsDto.getPercentage());
		content = content.replace("[Status]", reportsDto.getStatus());
		content = content.replace("[Attempts]", user.getAttempts()+"");
		content = content.replace("[TotalNoOfQns]", reportsDto.getTotalNoOfQuestions());
		content = content.replace("[CompletedOn]", now.format(dtf));
		
		String subject = env.getProperty("user.result.subject");
		if(team==null) {
			content = content.replace("[Team]", "NA");
			subject = subject.replace("[team]", "NA");
		}else {
			content = content.replace("[Team]", team.getTeam());
			subject = subject.replace("[team]", team.getTeam());
		}
		

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);// true indicates multipart message

		helper.setFrom(from); // <--- THIS IS IMPORTANT

		helper.setSubject(subject);
		helper.setTo(user.getEmail());
		helper.setCc(ccEmailIds);
		helper.setText(content, true);// true indicates body is html
		javaMailSender.send(message);
	}

	public void sendApproveOrDeclineMail(AdminRequest adminReq, String team) throws MessagingException {
		String content = env.getProperty("admin.request.content");
		content = content.replace("[Email]", adminReq.getEmail());
		content = content.replace("[Reason]", adminReq.getReason());
		content = content.replace("[RaisedOn]", adminReq.getReqRaisedOn().toString().split("T")[0]);
		content = content.replace("[AppOrDeclinedOn]", adminReq.getReqApproveOrDeclineOn().toString().split("T")[0]);
		content = content.replace("[Team]", team);

		String subject = env.getProperty("admin.request.subject");
		if (adminReq.isApprove()) {
			content = content.replace("[status]", "Approved");
			content = content.replace("[Password]", adminReq.getPassword());
		} else {
			content = content.replace("[Password]", "NA");
			content = content.replace("[status]", "Declined");
		}

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);// true indicates multipart message

		helper.setFrom(from); // <--- THIS IS IMPORTANT
		helper.setSubject(subject);
		helper.setTo(adminReq.getEmail());
		helper.setCc(ccEmailIds);
		helper.setText(content, true);// true indicates body is html
		javaMailSender.send(message);
	}

}
