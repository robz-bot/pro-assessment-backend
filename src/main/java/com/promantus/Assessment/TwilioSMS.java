package com.promantus.Assessment;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSMS {

	public static final String sendSMS(String number) {

		Twilio.init(AssessmentConstants.ACCOUNT_SID, AssessmentConstants.AUTH_TOKEN);

		Message message = Message.creator(new PhoneNumber(number), new PhoneNumber(AssessmentConstants.TWILIO_NUMBER),
				"Pro Assessment Results - 25/30. Pass").create();

		if (message.getStatus().equals("sent")) {
			return message.getStatus().toString();
		}
		return null;
	}
}
