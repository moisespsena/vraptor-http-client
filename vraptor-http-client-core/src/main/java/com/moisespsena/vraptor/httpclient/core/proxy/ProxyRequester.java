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
package com.moisespsena.vraptor.httpclient.core.proxy;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Factory;

import com.moisespsena.vraptor.httpclient.core.HttpObjectClientRequesterFactory;

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 15/07/2011
 * 
 */
public class ProxyRequester {
	public static <T> HttpObjectClientRequesterFactory<T> getRequesterFactory(
			final T object) {
		if (object instanceof Factory) {
			final Factory factory = (Factory) object;
			final Callback callback = factory.getCallbacks()[0];

			@SuppressWarnings("unchecked")
			final MethodInterceptorRequester<T> interceptor = (MethodInterceptorRequester<T>) callback;

			final HttpObjectClientRequesterFactory<T> requesterFactory = interceptor
					.getRequesterFactory();
			return requesterFactory;
		} else {
			return null;
		}
	}
}
