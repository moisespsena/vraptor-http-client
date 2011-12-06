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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 25/08/2011
 * 
 */
public class ContextURLCreator {
	private static final Logger logger = LoggerFactory
			.getLogger(ContextURLCreator.class);
	private static final String PKG = "com.moisespsena.vraptor.httpclient.variables.";
	public static final String SYSTEM_PROPERTY_CONTEXT_PATH = PKG
			+ "context.path";
	public static final String SYSTEM_PROPERTY_CONTEXT_PATH_URL = PKG
			+ "contextPathUrl";

	public static final String SYSTEM_PROPERTY_SERVER_HOST = PKG
			+ "server.host";
	public static final String SYSTEM_PROPERTY_SERVER_PORT = PKG
			+ "server.port";

	public static String build(final Object... values) {
		final StringBuilder b = new StringBuilder();

		for (final Object value : values) {
			b.append(value);
		}

		return b.toString();
	}

	private static String getProperty(final String propertyName,
			final String defaultValue) {
		String value = System.getenv(propertyName);
		if (value == null) {
			if (logger.isInfoEnabled()) {
				logger.info(build("The ENV value of '", propertyName,
						"' is not defined. search of System.getProperty(...)"));
			}

			value = System.getProperty(propertyName);
			if (value == null) {
				if (logger.isInfoEnabled()) {
					logger.info(build("The System.getProperty('", propertyName,
							"') is not defined. using default value"));
				}

				value = defaultValue;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(build("Property ", propertyName, "=", value));
		}

		return value;
	}

	public ContextURL createFromEnvVariables() {
		return createFromEnvVariables(null);
	}

	public ContextURL createFromEnvVariables(final String defaultContextPathURL) {
		String contextPathURL = getProperty(SYSTEM_PROPERTY_CONTEXT_PATH_URL,
				null);

		if (contextPathURL == null) {
			final String serverHost = getProperty(SYSTEM_PROPERTY_SERVER_HOST,
					"localhost");
			final int serverPort = Integer.parseInt(getProperty(
					SYSTEM_PROPERTY_SERVER_PORT, "8080"));
			final String contextPath = getProperty(
					SYSTEM_PROPERTY_CONTEXT_PATH, null);

			boolean error = false;
			if ((contextPath == null)) {
				if (defaultContextPathURL == null) {
					throw new InvalidPropertyException(
							SYSTEM_PROPERTY_CONTEXT_PATH,
							"The contextProperty has be null");
				} else {
					error = true;
				}
			} else if ("/".equals(contextPath)) {
				if (defaultContextPathURL == null) {
					throw new InvalidPropertyException(
							SYSTEM_PROPERTY_CONTEXT_PATH,
							"Set the Context Path not a '/' (Example: /${project.artifactId})");
				} else {
					error = true;
				}
			}

			if (error) {
				contextPathURL = defaultContextPathURL;
			} else {
				final StringBuilder b = new StringBuilder("http://");
				b.append(serverHost);

				if (serverPort != 80) {
					b.append(":");
					b.append(serverPort);
				}

				b.append(contextPath);

				contextPathURL = b.toString();
			}
		}

		return new ContextURL(contextPathURL);
	}
}
