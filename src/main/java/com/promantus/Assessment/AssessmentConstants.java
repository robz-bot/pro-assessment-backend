package com.promantus.Assessment;

public final class AssessmentConstants {

	private AssessmentConstants() throws InstantiationException {
		throw new InstantiationException("Instances of this type are forbidden.");
	}

//	Twilio Constants
	public static final String ACCOUNT_SID = "AC384cce51387f8cdd0ea857a011432e3b";
	public static final String AUTH_TOKEN = "cf5985ba4ce466cffb8d6c318a234eba";
	public static final String TWILIO_NUMBER = "+18156271503";
	
	
	public static final String APPROVE = "approve";
	public static final String DECLINE = "decline";
	
	/** INACTIVE - 0. */
	public static final int INACTIVE = 0;

	/** ACTIVE - 1. */
	public static final int ACTIVE = 1;

	/** RETURN_STATUS_OK - 0. */
	public static final int RETURN_STATUS_OK = 0;

	/** RETURN_STATUS_ERROR - 1. */
	public static final int RETURN_STATUS_ERROR = 1;

	public static final String TEAM_CODE = "TMCD";
	
//	Exam Status
	public static final String PASS = "Pass";
	public static final String FAIL = "Fail";

//	Search Types
	public static final String TYPE1 = "question";
	public static final String TYPE2 = "options";
	public static final String TYPE3 = "answer";
	public static final String TYPE4 = "team";
	public static final String TYPE5 = "username";
	public static final String TYPE6 = "status";
	public static final String TYPE7 = "percentage";
	public static final String TYPE8 = "date";
	public static final String TYPE9 = "attempts";
	public static final String TYPE10 = "empcode";
	public static final String TYPE11 = "email";
	public static final String TYPE12 = "manager";
	public static final String TYPE13 = "techQuestions";
	public static final String TYPE14 = "genQuestions";

//	Percentage Range
	public static final String RANGE0 = "0-25";
	public static final String RANGE1 = "26-50";
	public static final String RANGE2 = "51-75";
	public static final String RANGE3 = "76-100";

//	Icon Style
	public static final String ATTEMPT_ICON_STYLE = "bi bi-compass light font-large-2";
	public static final String USER_ICON_STYLE = "bi bi-people-fill warning font-large-2";
	public static final String TEAM_ICON_STYLE = "bi bi-hdd-stack-fill primary font-large-2";
	public static final String PASS_ICON_STYLE = "bi bi-check-circle-fill success font-large-2";
	public static final String FAIL_ICON_STYLE = "bi bi-x-circle-fill danger font-large-2";
	public static final String QUES_ICON_STYLE = "bi bi-question-circle-fill white font-large-2";

}