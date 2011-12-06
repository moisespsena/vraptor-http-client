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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.resource.HttpMethod;

import com.moisespsena.vraptor.advancedrequest.RequestResult;
import com.moisespsena.vraptor.advancedrequest.ResourceMethodRequest;
import com.moisespsena.vraptor.advancedrequest.ResourceMethodRequestImpl;
import com.moisespsena.vraptor.javaobjectserialization.JavaObjectRequestStatic;
import com.moisespsena.vraptor.modularvalidator.CategorizedMessages;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 13/09/2011
 */
public class HttpObjectClientRequester {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpObjectClientRequester.class);

	public static String stringResult(final InputStream is) throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(
				is));
		final StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
		is.close();
		return sb.toString();
	}

	private final String contextPathURL;
	private final HttpObjectClientCookieManager cookieManager;
	private CategorizedMessages lastMessages;
	private RequestResult lastRequestResult;
	private final Object[] parameters;
	private final Class<?> resourceClass;

	private final Method resourceMethod;
	private HttpObjectClientRequesterResult result;

	public HttpObjectClientRequester(final String contextPathURL,
			final Class<?> resourceClass, final Method resourceMethod,
			final Object[] parameters,
			final HttpObjectClientCookieManager cookieManager) {
		this.contextPathURL = contextPathURL;
		this.resourceClass = resourceClass;
		this.resourceMethod = resourceMethod;
		this.parameters = parameters;
		this.cookieManager = cookieManager;
	}

	private void extractResult(final InputStream in) {
		try {
			final ObjectInputStream input = new ObjectInputStream(in);

			Object result;
			result = input.readObject();

			if (result instanceof RequestResult) {
				lastRequestResult = (RequestResult) result;
				lastMessages = lastRequestResult.getFlashMessages();
			}
		} catch (final IOException e) {
			throw new HttpObjectClientException(e);
		} catch (final ClassNotFoundException e) {
			throw new HttpObjectClientException(e);
		}
	}

	/**
	 * @return the contextPathURL
	 */
	public String getContextPathURL() {
		return contextPathURL;
	}

	/**
	 * @return the cookieManager
	 */
	public HttpObjectClientCookieManager getCookieManager() {
		return cookieManager;
	}

	private HttpMethod getHttpMethod() {
		if (resourceMethod.isAnnotationPresent(Get.class)) {
			return HttpMethod.GET;
		} else if (resourceMethod.isAnnotationPresent(Post.class)) {
			return HttpMethod.POST;
		} else if (resourceMethod.isAnnotationPresent(Put.class)) {
			return HttpMethod.PUT;
		} else if (resourceMethod.isAnnotationPresent(Delete.class)) {
			return HttpMethod.DELETE;
		} else {
			return HttpMethod.GET;
		}
	}

	/**
	 * @return the lastMessages
	 */
	public CategorizedMessages getLastMessages() {
		return lastMessages;
	}

	public RequestResult getLastRequestResult() {
		return lastRequestResult;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public Class<?> getResourceClass() {
		return resourceClass;
	}

	/**
	 * @return the resourceMethod
	 */
	public Method getResourceMethod() {
		return resourceMethod;
	}

	/**
	 * @return the result
	 */
	public HttpObjectClientRequesterResult getResult() {
		return result;
	}

	private boolean isObjectResult(final HttpURLConnection urlConnection) {
		String headerName = null;
		for (int i = 1; (headerName = urlConnection.getHeaderFieldKey(i)) != null; i++) {
			if (headerName.equals("Content-Type")) {
				final String contentType = urlConnection.getHeaderField(i);

				if (JavaObjectRequestStatic.isValid(contentType)) {
					return true;
				} else {
					return false;
				}
			}
		}

		return false;
	}

	public void request() {
		HttpURLConnection urlConnection = null;

		final String contextPathURL = JavaObjectRequestStatic
				.hydrateContextPathURL(this.contextPathURL);

		InputStream in = null;

		String stringResult = null;

		try {
			final URL servlet = new URL(contextPathURL);
			urlConnection = (HttpURLConnection) servlet.openConnection();
			JavaObjectRequestStatic.hydateUrlConnection(urlConnection);

			if (cookieManager != null) {
				cookieManager.hydrate(urlConnection);
			}

			final HttpMethod httpMethod = getHttpMethod();
			if (logger.isDebugEnabled()) {
				logger.debug("Request Java Object from [{}:{}]",
						httpMethod.name(), contextPathURL);
			}

			// Write the arguments as post data
			final ObjectOutputStream out = new ObjectOutputStream(
					urlConnection.getOutputStream());

			final ResourceMethodRequest methodRequest = new ResourceMethodRequestImpl(
					httpMethod, resourceClass.getName(),
					resourceMethod.getName(), parameters);

			out.writeObject(methodRequest);

			out.flush();
			out.close();

			in = urlConnection.getInputStream();

			final boolean isObjectResult = isObjectResult(urlConnection);

			if (isObjectResult) {
				extractResult(in);
			} else {
				stringResult = stringResult(in);
			}

			result = new HttpObjectClientRequesterResult(lastRequestResult,
					stringResult, isObjectResult, !isObjectResult, false);
		} catch (final IOException e) {
			try {
				final int code = urlConnection.getResponseCode();
				in = urlConnection.getErrorStream();

				final boolean isObjectResult = isObjectResult(urlConnection);

				if (isObjectResult) {
					extractResult(in);
				} else {
					stringResult = stringResult(in);
				}

				if (code == HttpURLConnection.HTTP_NO_CONTENT) {
					result = new HttpObjectClientRequesterResult();
				} else {
					result = new HttpObjectClientRequesterResult(
							lastRequestResult, stringResult, isObjectResult,
							!isObjectResult, false);
					if (code == HttpURLConnection.HTTP_PRECON_FAILED) {
						throw new HttpObjectClientValidationException(result,
								code, e);
					} else {
						throw new HttpObjectClientStatusCodeException(result,
								code, e);
					}
				}
			} catch (final IOException e1) {
				throw new HttpObjectClientException(e);
			}
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					throw new HttpObjectClientException(e);
				}
			}
			if (urlConnection != null) {
				if (cookieManager != null) {
					cookieManager.load(urlConnection);
				}
			}
		}
	}

	/**
	 * @param lastMessages
	 *            the lastMessages to set
	 */
	public void setLastMessages(final CategorizedMessages lastMessages) {
		this.lastMessages = lastMessages;
	}
}
