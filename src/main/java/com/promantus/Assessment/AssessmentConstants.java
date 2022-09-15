package com.promantus.Assessment;

public final class AssessmentConstants {

	private AssessmentConstants() throws InstantiationException {
		throw new InstantiationException("Instances of this type are forbidden.");
	}

	/** INACTIVE - 0. */
	public static final int INACTIVE = 0;

	/** ACTIVE - 1. */
	public static final int ACTIVE = 1;

	/** RETURN_STATUS_OK - 0. */
	public static final int RETURN_STATUS_OK = 0;

	/** RETURN_STATUS_ERROR - 1. */
	public static final int RETURN_STATUS_ERROR = 1;

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