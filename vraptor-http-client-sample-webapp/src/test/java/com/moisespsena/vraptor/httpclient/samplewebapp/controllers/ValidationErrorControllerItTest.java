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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.moisespsena.vraptor.httpclient.core.HttpObjectClient;
import com.moisespsena.vraptor.httpclient.core.HttpObjectClientCreator;
import com.moisespsena.vraptor.httpclient.core.HttpObjectClientValidationException;
import com.moisespsena.vraptor.httpclient.core.StaticContextURLCreator;
import com.moisespsena.vraptor.httpclient.test.HttpObjectClientCreatorMock;
import com.moisespsena.vraptor.modularvalidator.CategorizedMessages;
import com.moisespsena.vraptor.modularvalidator.SimpleMessage;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 13/09/2011
 */

public class ValidationErrorControllerItTest {
	private ValidationErrorController controller;

	@Before
	public void init() throws Exception {
		final HttpObjectClientCreator clientCreator = new HttpObjectClientCreatorMock();
		final HttpObjectClient client = clientCreator
				.create(StaticContextURLCreator
						.createFromEnvVariables(DefaultURL.URL));

		controller = client.from(ValidationErrorController.class);
	}

	@Test
	public void simpleErrorTest() {
		try {
			controller.simpleError();
			Assert.fail("error expected");
		} catch (final HttpObjectClientValidationException e) {
			final CategorizedMessages messages = e.getCategorizedMessages();
			Assert.assertNotNull("messages is null", messages);
			Assert.assertNotNull("messages.messages is null",
					messages.getMessages());
			Assert.assertEquals(1, messages.getMessages().length);

			final SimpleMessage message = messages.getMessages()[0];

			Assert.assertEquals(ValidationErrorController.CATEGORY,
					message.getCategory());
			Assert.assertEquals(ValidationErrorController.MESSAGE,
					message.getMessage());
		}
	}
}
