/***
 * Copyright (c) 2011 Moises P. Sena - www.moisespsena.com
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package com.moisespsena.vraptor.httpclient.core;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.moisespsena.vraptor.modularvalidator.CategorizedMessages;
import com.moisespsena.vraptor.modularvalidator.SimpleMessage;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 02/09/2011
 */
public class HttpObjectClientValidationException extends
		HttpObjectClientStatusCodeException {

	private static final long serialVersionUID = 884719703804513188L;

	private static String messagesToString(final SimpleMessage... errors) {
		final StringBuffer b = new StringBuffer("[");
		final int max = errors.length - 1;

		for (int i = 0; i < max; i++) {
			final SimpleMessage message = errors[i];
			b.append(messageToString(message));
			b.append(",");
		}

		b.append(messageToString(errors[errors.length - 1]));
		b.append("]");

		final String str = b.toString();
		return str;
	}

	private static String messageToString(final SimpleMessage message) {
		final StringBuffer b = new StringBuffer("{\"category\":\"");
		b.append(message.getCategory().replaceAll("\"", "\\\""));
		b.append("\",\"message\":\"");
		b.append(message.getMessage().replaceAll("\"", "\\\""));
		b.append("\"}");
		return b.toString();
	}

	/**
	 * @param result
	 * @param statusCode
	 * @param message
	 */
	public HttpObjectClientValidationException(
			final HttpObjectClientRequesterResult result, final int statusCode,
			final String message) {
		super(result, statusCode, message);
	}

	/**
	 * @param result
	 * @param statusCode
	 * @param throwable
	 */
	public HttpObjectClientValidationException(
			final HttpObjectClientRequesterResult result, final int statusCode,
			final Throwable throwable) {
		super(result, statusCode, throwable);
	}

	/**
	 * @param statusCode
	 * @param message
	 */
	public HttpObjectClientValidationException(final int statusCode,
			final String message) {
		super(statusCode, message);
	}

	/**
	 * @param statusCode
	 * @param message
	 * @param cause
	 */
	public HttpObjectClientValidationException(final int statusCode,
			final String message, final Throwable cause) {
		super(statusCode, message, cause);
	}

	public CategorizedMessages getCategorizedMessages() {
		return getResult().getRequestResult().getFlashMessages();
	}

	@Override
	public String toString() {
		final String str = new ToStringBuilder(this).append("messages",
				messagesToString(getCategorizedMessages().getMessages()))
				.toString();
		return str;
	}
}
