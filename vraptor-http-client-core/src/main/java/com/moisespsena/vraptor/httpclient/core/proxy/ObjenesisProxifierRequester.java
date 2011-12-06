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

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.ObjenesisProxifier;
import br.com.caelum.vraptor.proxy.ProxyInvocationException;
import br.com.caelum.vraptor.proxy.SuperMethod;

import com.moisespsena.vraptor.httpclient.core.HttpObjectClientRequesterFactory;

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 15/07/2011
 * 
 */
public class ObjenesisProxifierRequester extends ObjenesisProxifier {
	@SuppressWarnings("rawtypes")
	@Override
	protected MethodInterceptor cglibMethodInterceptor(
			final MethodInvocation handler) {

		return new MethodInterceptorRequester() {
			@Override
			public HttpObjectClientRequesterFactory getRequesterFactory() {
				final HttpObjectClientRequesterFactory implementation = ((MethodInvocationRequester) handler)
						.getRequesterFactory();
				return implementation;
			}

			@SuppressWarnings("unchecked")
			@Override
			public Object intercept(final Object proxy, final Method method,
					final Object[] args, final MethodProxy methodProxy) {
				return handler.intercept(proxy, method, args,
						new SuperMethod() {
							@Override
							public Object invoke(final Object proxy,
									final Object[] args) {
								try {
									return methodProxy.invokeSuper(proxy, args);
								} catch (final Throwable throwable) {
									throw new ProxyInvocationException(
											throwable);
								}
							}
						});
			}
		};
	}
}
