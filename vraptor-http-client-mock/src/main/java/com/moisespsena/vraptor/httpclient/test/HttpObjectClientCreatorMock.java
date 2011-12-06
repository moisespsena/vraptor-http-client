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
package com.moisespsena.vraptor.httpclient.test;

import com.moisespsena.vraptor.httpclient.core.DefaultHttpObjectClientCreator;
import com.moisespsena.vraptor.httpclient.core.proxy.RequesterProxifierFactory;

/**
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 25/08/2011
 * 
 */
public class HttpObjectClientCreatorMock extends DefaultHttpObjectClientCreator {
	private static RequesterProxifierFactory proxifierFactory;

	protected static RequesterProxifierFactory getProxifierFactory() {
		if (proxifierFactory == null) {
			proxifierFactory = new RequesterProxifierFactory();
		}

		return proxifierFactory;
	}

	public HttpObjectClientCreatorMock() {
		this(getProxifierFactory());
	}

	public HttpObjectClientCreatorMock(
			final RequesterProxifierFactory proxifierFactory) {
		super(proxifierFactory);
	}
}
