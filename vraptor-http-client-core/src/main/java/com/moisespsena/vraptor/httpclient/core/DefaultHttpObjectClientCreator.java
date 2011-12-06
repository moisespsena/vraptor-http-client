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

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

import com.moisespsena.vraptor.httpclient.core.proxy.RequesterProxifierFactory;

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 25/08/2011
 * 
 */
@Component
@ApplicationScoped
public class DefaultHttpObjectClientCreator implements HttpObjectClientCreator {

	private final RequesterProxifierFactory proxifierFactory;

	public DefaultHttpObjectClientCreator(
			final RequesterProxifierFactory proxifierFactory) {
		this.proxifierFactory = proxifierFactory;
	}

	@Override
	public HttpObjectClient create(final ContextURL contextURL) {
		return new HttpObjectClient(contextURL, proxifierFactory);
	}

	@Override
	public HttpObjectClient create(final String contextPathURL) {
		return create(new ContextURL(contextPathURL));
	}

}
