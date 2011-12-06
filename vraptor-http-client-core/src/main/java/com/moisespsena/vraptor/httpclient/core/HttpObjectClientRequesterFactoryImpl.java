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

import java.lang.reflect.Method;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 13/09/2011
 */
public class HttpObjectClientRequesterFactoryImpl<T> implements
		HttpObjectClientRequesterFactory<T> {

	private final ContextURL contextURL;

	private final HttpObjectClientCookieManager cookieManager;
	private HttpObjectClientRequester lastRequester;
	private final Class<T> resourceClass;

	public HttpObjectClientRequesterFactoryImpl(final Class<T> resourceClass,
			final ContextURL contextURL,
			final HttpObjectClientCookieManager cookieManager) {
		this.resourceClass = resourceClass;
		this.contextURL = contextURL;
		this.cookieManager = cookieManager;
	}

	@Override
	public HttpObjectClientRequester createRequester(
			final Method resourceMethod, final Object[] parameters) {
		try {
			final String contextPathURL = contextURL.getContextPathUrl();
			lastRequester = new HttpObjectClientRequester(contextPathURL,
					resourceClass, resourceMethod, parameters, cookieManager);
			return lastRequester;
		} catch (final Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new HttpObjectClientException(e);
			}
		}
	}

	/**
	 * @return the contextURL
	 */
	@Override
	public ContextURL getContextURL() {
		return contextURL;
	}

	@Override
	public HttpObjectClientCookieManager getCookieManager() {
		return cookieManager;
	}

	@Override
	public HttpObjectClientRequester getLastRequester() {
		return lastRequester;
	}

	/**
	 * @return the resourceClass
	 */
	@Override
	public Class<T> getResourceClass() {
		return resourceClass;
	}
}
