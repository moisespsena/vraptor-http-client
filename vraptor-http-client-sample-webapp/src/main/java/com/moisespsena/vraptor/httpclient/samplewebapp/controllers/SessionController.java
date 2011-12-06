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

import br.com.caelum.vraptor.Resource;

import com.moisespsena.vraptor.httpclient.samplewebapp.components.SessionComponent;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 13/09/2011
 */
@Resource
public class SessionController {
	private final SessionComponent sessionComponent;

	public SessionController(final SessionComponent sessionComponent) {
		this.sessionComponent = sessionComponent;
	}

	public Date getValue() {
		return sessionComponent.getParameterValue();
	}

	public void setValue(final Date value) {
		sessionComponent.setParameterValue(value);
	}
}
