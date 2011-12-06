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

import com.moisespsena.vraptor.advancedrequest.RequestResult;

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 25/08/2011
 * 
 */
public class HttpObjectClientStatusCodeException extends RuntimeException {
	private static final long serialVersionUID = -8208394178060027663L;
	private HttpObjectClientRequesterResult result;
	private final int statusCode;

	public HttpObjectClientStatusCodeException(
			final HttpObjectClientRequesterResult result, final int statusCode,
			final String message) {
		super(message);
		this.result = result;
		this.statusCode = statusCode;
	}

	public HttpObjectClientStatusCodeException(
			final HttpObjectClientRequesterResult result, final int statusCode,
			final Throwable throwable) {
		super(throwable);
		this.result = result;
		this.statusCode = statusCode;
	}

	public HttpObjectClientStatusCodeException(final int statusCode,
			final String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public HttpObjectClientStatusCodeException(final int statusCode,
			final String message, final Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public RequestResult getRequestResult() {
		return result.getRequestResult();
	}

	/**
	 * @return the result
	 */
	public HttpObjectClientRequesterResult getResult() {
		return result;
	}

	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(final HttpObjectClientRequesterResult result) {
		this.result = result;
	}
}