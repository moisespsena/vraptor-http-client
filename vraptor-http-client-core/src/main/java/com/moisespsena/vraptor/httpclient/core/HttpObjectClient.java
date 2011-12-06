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

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.proxy.SuperMethod;

import com.moisespsena.vraptor.httpclient.core.proxy.MethodInvocationRequester;
import com.moisespsena.vraptor.httpclient.core.proxy.RequesterProxifierFactory;

/**
 * Http Cliente baseado em Object Notation
 * 
 * <pre>
 * // MyController.java
 * <code>package controllers;
 * 
 * \@{@link br.com.caelum.vraptor.Resource}
 * class MyController {
 *  
 *  	\@{@link Get}("/my/fullName/{name}/{lastName}")
 * 	public String fullName(String name, String lastName) {
 * 		return name + " " + lastName;
 * 	}
 * }</code>
 * </pre>
 * 
 * <pre>
 * <code>HttpObjectClient client = new {@link HttpObjectClient}(new {@link ContextURL}("http://localhost:8080/contextPath"));
 * String fullName = client.from(MyController.class).fullName("Moises", "P. Sena");
 * 
 * // show Moises P. Sena
 * System.ou.println(fullName);</code>
 * </pre>
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 19/08/2011
 * 
 */
public class HttpObjectClient {
	private final ContextURL contextURL;
	private final HttpObjectClientCookieManager cookieManager = new HttpObjectClientCookieManager();

	private final RequesterProxifierFactory proxifierFactory;

	public HttpObjectClient(final ContextURL contextURL,
			final RequesterProxifierFactory proxifierFactory) {
		this.contextURL = contextURL;
		this.proxifierFactory = proxifierFactory;
	}

	public synchronized <T> HttpObjectClientRequester createRequester(
			final Class<T> resourceClass, final Method resourceMethod,
			final Object[] parameters) {

		final HttpObjectClientRequesterFactory<T> requesterFactory = new HttpObjectClientRequesterFactoryImpl<T>(
				resourceClass, contextURL, cookieManager);
		final HttpObjectClientRequester requester = requesterFactory
				.createRequester(resourceMethod, parameters);

		return requester;
	}

	public synchronized <T> HttpObjectClientRequesterFactory<T> createRequesterFactory(
			final Class<T> resourceClass) {
		final HttpObjectClientRequesterFactory<T> requesterFactory = new HttpObjectClientRequesterFactoryImpl<T>(
				resourceClass, contextURL, cookieManager);

		return requesterFactory;
	}

	public synchronized <T> T from(final Class<T> resourceClass) {
		final HttpObjectClientRequesterFactory<T> requesterFactory = createRequesterFactory(resourceClass);

		final MethodInvocationRequester<T> invocationRequester = new MethodInvocationRequester<T>() {
			@Override
			public HttpObjectClientRequesterFactory<T> getRequesterFactory() {
				return requesterFactory;
			}

			@Override
			public Object intercept(final T proxy, final Method resourceMethod,
					final Object[] parameters, final SuperMethod superMethod) {
				final HttpObjectClientRequester requester = requesterFactory
						.createRequester(resourceMethod, parameters);

				requester.request();

				final Object result = requester.getResult().getRequestResult()
						.getResourceMethodResult().getResult();
				return result;
			}
		};

		final Proxifier proxifier = proxifierFactory.getInstance();

		return proxifier.proxify(resourceClass, invocationRequester);
	}

	public HttpObjectClientCookieManager getCookieManager() {
		return cookieManager;
	}
}
