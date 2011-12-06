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

import java.io.Serializable;

import com.moisespsena.vraptor.advancedrequest.RequestResult;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 15/09/2011
 */
public class HttpObjectClientRequesterResult implements Serializable {
	private static final long serialVersionUID = 5353808085686703184L;
	private final boolean empty;
	private final boolean object;

	private final RequestResult requestResult;
	private final boolean string;
	private final String stringResult;

	public HttpObjectClientRequesterResult() {
		this(null, null, true, true, true);
	}

	/**
	 * @param requestResult
	 * @param stringResult
	 * @param object
	 * @param string
	 * @param empty
	 */
	public HttpObjectClientRequesterResult(final RequestResult requestResult,
			final String stringResult, final boolean object,
			final boolean string, final boolean empty) {
		super();
		this.requestResult = requestResult;
		this.stringResult = stringResult;
		this.object = object;
		this.string = string;
		this.empty = empty;
	}

	/**
	 * @return the requestResult
	 */
	public RequestResult getRequestResult() {
		return requestResult;
	}

	/**
	 * @return the stringResult
	 */
	public String getStringResult() {
		return stringResult;
	}

	/**
	 * @return the empty
	 */
	public boolean isEmpty() {
		return empty;
	}

	/**
	 * @return the object
	 */
	public boolean isObject() {
		return object;
	}

	/**
	 * @return the string
	 */
	public boolean isString() {
		return string;
	}
}
