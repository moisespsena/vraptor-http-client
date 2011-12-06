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

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 22/08/2011
 * 
 */
public class InvalidPropertyException extends RuntimeException {

	private static final long serialVersionUID = 2065712915968122772L;

	public InvalidPropertyException() {
	}

	/**
	 * @param propertyName
	 */
	public InvalidPropertyException(final String propertyName) {
		super("The property " + propertyName + " has not be a valid value");
	}

	/**
	 * @param propertyName
	 * @param explanation
	 */
	public InvalidPropertyException(final String propertyName,
			final String explanation) {
		super("The property " + propertyName + " has not be a valid value: "
				+ explanation);
	}

}
