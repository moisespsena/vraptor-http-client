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

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.view.EmptyResult;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 13/09/2011
 */
@Resource
public class ValidationErrorController {
	public static final String CATEGORY = "test-error-category";
	public static final String MESSAGE = "test error message";
	private final Validator validator;

	public ValidationErrorController(final Validator validator) {
		this.validator = validator;
	}

	public String simpleError() {
		validator.add(new Message() {
			private static final long serialVersionUID = 3682542301758876481L;

			@Override
			public String getCategory() {
				return CATEGORY;
			}

			@Override
			public String getMessage() {
				return MESSAGE;
			}
		});

		validator.onErrorUse(EmptyResult.class);

		return "ok";
	}
}
