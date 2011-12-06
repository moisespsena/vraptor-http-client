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
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 29/08/2011
 */
public class StaticContextURLCreator {
	private static ContextURLCreator contextURLCreator;
	private static ContextURL fromEnv;

	private static ContextURL fromEnvWithDefault;

	public static ContextURL createFromEnvVariables() {
		if (fromEnv == null) {
			fromEnv = getContextURLCreator().createFromEnvVariables();
		}

		return fromEnv;
	}

	public static ContextURL createFromEnvVariables(
			final String defaultContextPathURL) {
		if (fromEnvWithDefault == null) {
			fromEnvWithDefault = getContextURLCreator().createFromEnvVariables(
					defaultContextPathURL);
		}

		return fromEnvWithDefault;
	}

	private static ContextURLCreator getContextURLCreator() {
		if (contextURLCreator == null) {
			contextURLCreator = new ContextURLCreator();
		}

		return contextURLCreator;
	}

}
