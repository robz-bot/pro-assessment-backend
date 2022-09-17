package com.promantus.Assessment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;

import org.springframework.data.domain.Sort;




public final class AssessmentUtil {
	
	/** Characters allowed to create UUID. */
	private static final String UUID_STR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/** Characters allowed to create UUID_OTP. */
	private static final String UUID_OTP = "0123456789";

	/** Length of each part of the UUID. */
	private static final int UUID_LEN = 6;
	
	public static Sort orderByCreatedDateTimeAsc() {
		return Sort.by(Sort.Direction.ASC, "createdDateTime");
	}

	/**
	 * @return
	 */
	public static Sort orderByCreatedDateTimeDesc() {
		return Sort.by(Sort.Direction.DESC, "createdDateTime");
	}

	/**
	 * @return
	 */
	public static Sort orderByUpdatedOnDesc() {
		return Sort.by(Sort.Direction.DESC, "updatedOn");
	}


	public static String generateUUID(Integer uuidLength) {

		if (uuidLength == null || uuidLength == 0) {
			uuidLength = UUID_LEN;
		}
		Random random = new SecureRandom();
		StringBuilder uuid = new StringBuilder();

		for (int i = 0; i < uuidLength; i++) {
			uuid.append(UUID_STR.charAt(random.nextInt(UUID_STR.length())));
		}

		return uuid.toString();
	}
	
	public static String getOTP() {

		Random random = new SecureRandom();
		StringBuilder uuid = new StringBuilder();

		for (int i = 0; i < UUID_LEN; i++) {
			uuid.append(UUID_OTP.charAt(random.nextInt(UUID_OTP.length())));
		}

		return uuid.toString();
	}
	
	public static String encrypt(String stringToEncrypt) throws Exception {

		byte[] message = stringToEncrypt.getBytes(StandardCharsets.UTF_8);
		return Base64.getEncoder().encodeToString(message);
	}

	public static String decrypt(String stringToDecrypt) throws Exception {

		byte[] decoded = Base64.getDecoder().decode(stringToDecrypt);
		return new String(decoded, StandardCharsets.UTF_8);
	}

	public static String formatDateTime(LocalDateTime localDateTime) throws Exception {

		DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a");
		return FOMATTER.format(localDateTime);
	}

	public static LocalDateTime getGMTDateTime(LocalDateTime localDateTime) {

		return LocalDateTime.ofInstant(localDateTime.atZone(ZoneId.systemDefault()).toInstant(), ZoneId.of("GMT"));
	}

	
	public static LocalDateTime getLocalDateTime(LocalDateTime localDateTime, String zoneId) {

		return LocalDateTime.ofInstant(localDateTime.atZone(ZoneId.of("GMT")).toInstant(), ZoneId.of(zoneId));
	}

	
	public static LocalDateTime getISTLocalDateTime(LocalDateTime localDateTime) {

		return LocalDateTime.ofInstant(localDateTime.atZone(ZoneId.of("GMT")).toInstant(), ZoneId.of("Asia/Kolkata"));
	}


	public static synchronized String getErrorMessage(final Exception e) {

		final StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}

	public static Sort orderByUpdatedDateTimeDesc() {
		return Sort.by(Sort.Direction.DESC, "updatedDateTime");
	}
	

}
