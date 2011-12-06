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
package com.moisespsena.vraptor.httpclient.samplewebapp.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.moisespsena.vraptor.httpclient.core.HttpObjectClient;
import com.moisespsena.vraptor.httpclient.core.HttpObjectClientCreator;
import com.moisespsena.vraptor.httpclient.core.StaticContextURLCreator;
import com.moisespsena.vraptor.httpclient.test.HttpObjectClientCreatorMock;

public class MainControllerItTest {
	private MainController controller;

	@Before
	public void init() throws Exception {
		final HttpObjectClientCreator clientCreator = new HttpObjectClientCreatorMock();
		final HttpObjectClient client = clientCreator
				.create(StaticContextURLCreator
						.createFromEnvVariables("http://localhost:8080/vraptor-http-client-sample-webapp"));
		controller = client.from(MainController.class);
	}

	@Test
	public void testParam() {
		final String result = controller.paramMethod("teste");

		Assert.assertEquals("teste", result);
	}

	@Test
	public void testPost() {
		final Product result = controller.postMethod("abacate", new Product(
				"Goiaba"));

		Assert.assertNotNull(result);
		Assert.assertEquals("Goiaba", result.getName());
	}

	@Test
	public void testPostAdvancedParam() {
		final Product product = new Product("Goiaba");
		final List<Product> users = new ArrayList<Product>();
		final Product p2 = new Product("mamao");
		users.add(p2);
		users.add(new Product("abacaxi"));

		final List<Product> users2 = new ArrayList<Product>();
		users2.add(new Product("java"));

		p2.setUsers(users2);

		product.setUsers(users);

		final Map<Long, Integer> data = new HashMap<Long, Integer>();
		data.put(1L, 10);
		data.put(2L, 20);

		product.setData(data);

		final Product result = controller.postMethod("blabla", product);

		Assert.assertNotNull(result);
		Assert.assertEquals("Goiaba", result.getName());
		Assert.assertNotNull(result.getData());
		Assert.assertTrue(2 == result.getData().size());
		Assert.assertTrue(10 == result.getData().get(1L));
		Assert.assertTrue(20 == result.getData().get(2L));
		Assert.assertNotNull(result.getUsers());
		Assert.assertEquals(2, result.getUsers().size());
		Assert.assertEquals("mamao", result.getUsers().get(0).getName());
		Assert.assertEquals("abacaxi", result.getUsers().get(1).getName());
		Assert.assertNotNull(result.getUsers().get(0).getUsers());
		Assert.assertEquals("java", result.getUsers().get(0).getUsers().get(0)
				.getName());
	}

	@Test
	public void testSimple() {
		final String result = controller.simpleMethod();
		Assert.assertEquals("ok", result);
	}

}
