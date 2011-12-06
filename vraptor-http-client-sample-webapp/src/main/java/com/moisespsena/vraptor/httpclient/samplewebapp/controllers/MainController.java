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

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;

@Resource
public class MainController {

	public MainController() {
	}

	@Get("/main/param")
	public String paramMethod(final String param) {
		return param;
	}

	@Post("/main/param")
	public Product postMethod(final String a, final Product product) {
		return product;
	}

	@Get("/main/simple")
	public String simpleMethod() {
		return "ok";
	}

}
