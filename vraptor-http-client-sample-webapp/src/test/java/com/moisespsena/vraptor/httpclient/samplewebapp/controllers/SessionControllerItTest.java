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
package com.moisespsena.vraptor.httpclient.samplewebapp.controllers;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.moisespsena.vraptor.httpclient.core.HttpObjectClient;
import com.moisespsena.vraptor.httpclient.core.HttpObjectClientCreator;
import com.moisespsena.vraptor.httpclient.core.HttpObjectClientRequesterFactory;
import com.moisespsena.vraptor.httpclient.core.StaticContextURLCreator;
import com.moisespsena.vraptor.httpclient.core.proxy.ProxyRequester;
import com.moisespsena.vraptor.httpclient.test.HttpObjectClientCreatorMock;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 13/09/2011
 */
public class SessionControllerItTest {

	private SessionController controller;

	@Before
	public void init() throws Exception {
		final HttpObjectClientCreator clientCreator = new HttpObjectClientCreatorMock();
		final HttpObjectClient client = clientCreator
				.create(StaticContextURLCreator
						.createFromEnvVariables(DefaultURL.URL));

		controller = client.from(SessionController.class);
	}

	@Test
	public void testValuesOfUserSession() {
		final Date value = new Date();
		final HttpObjectClientRequesterFactory<SessionController> factory = ProxyRequester
				.getRequesterFactory(controller);
		factory.getCookieManager().setAccept(true);

		controller.setValue(value);

		final Date serverValue = controller.getValue();

		Assert.assertEquals(value, serverValue);
	}

}
