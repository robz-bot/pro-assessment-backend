
package com.promantus.Assessment.Service;

public interface CommonService {

	/**
	 * @return
	 */
	public long nextSequenceNumber() throws Exception;

	/**
	 * @param messageKey
	 * @param language
	 * @return
	 */
	String getMessage(String messageKey, String[] params, String lang) throws Exception;

}
