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


/**
 * Resolvedor de Enderecos do Contexto do Aplicacao WEB
 * 
 * @author Moises P. Sena &lt;moisespsena@gmail.com&gt;
 * @since 1.0 22/08/2011
 * 
 */
public class ContextURL {

	private String contextPath;
	private String contextPathURL;
	private String serverHost;
	private int serverPort;

	public ContextURL(final String contextPathURL) {
		this.contextPathURL = contextPathURL;
	}

	public ContextURL(final String serverHost, final int serverPort,
			final String contextPath) {
		this.contextPath = contextPath;
		this.serverPort = serverPort;
		this.serverHost = serverHost;
	}

	public String getContextPath() {
		return contextPath;
	}

	public String getContextPathUrl() {
		return contextPathURL;
	}

	public String getServerHost() {
		return serverHost;
	}

	public int getServerPort() {
		return serverPort;
	}
}
