package com.promantus.Assessment;

import com.twilio.Twilio;
import com.twilio.rest.lookups.v1.PhoneNumber;

public class TwilioMethods {

	public static final boolean verifyPhnNumber(String number) {

		Twilio.init(AssessmentConstants.ACCOUNT_SID, AssessmentConstants.AUTH_TOKEN);
		PhoneNumber phnNum = null;
		try {
			phnNum = PhoneNumber.fetcher(new com.twilio.type.PhoneNumber(number)).fetch();
		} catch (Exception e) {
			return false;
		}

		System.err.println(phnNum.getCountryCode());

		if (phnNum.getCountryCode() != null) {
			return true;
		}

		return false;

	}

}
