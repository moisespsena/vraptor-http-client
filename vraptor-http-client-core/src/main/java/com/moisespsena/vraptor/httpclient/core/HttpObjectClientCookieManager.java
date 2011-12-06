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

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 13/09/2011
 */
public class HttpObjectClientCookieManager {
	private boolean accept = false;
	private Map<String, String> cookies = new HashMap<String, String>();

	public void clear() {
		cookies = new HashMap<String, String>();
	}

	public void get(final String key) {
		cookies.get(key);
	}

	public Map<String, String> getCookiesMap() {
		return cookies;
	}

	public synchronized void hydrate(final HttpURLConnection urlConnection) {
		if (!accept) {
			return;
		}

		final Iterator<Entry<String, String>> it = cookies.entrySet()
				.iterator();

		final StringBuilder cookieBuilder = new StringBuilder();

		while (it.hasNext()) {
			final Entry<String, String> entry = it.next();
			cookieBuilder.append(entry.getKey());
			cookieBuilder.append('=');
			cookieBuilder.append(entry.getValue());

			if (it.hasNext()) {
				cookieBuilder.append(';');
				cookieBuilder.append(' ');
			}
		}

		final String cookieStr = cookieBuilder.toString();
		urlConnection.setRequestProperty("Cookie", cookieStr);
	}

	/**
	 * @return the accept
	 */
	public boolean isAccept() {
		return accept;
	}

	public synchronized void load(final HttpURLConnection urlConnection) {
		if (!accept) {
			return;
		}

		String headerName = null;
		for (int i = 1; (headerName = urlConnection.getHeaderFieldKey(i)) != null; i++) {
			if (headerName.equals("Set-Cookie")) {
				final String cookie = urlConnection.getHeaderField(i);
				parseCookie(cookie);
				break;
			}
		}
	}

	private void parseCookie(final String cookieStr) {
		cookies = new HashMap<String, String>(0);
		final String[] cookiesArr = cookieStr.split("\\s*;\\s*");

		for (final String cookie : cookiesArr) {
			final String[] kv = cookie.split("\\s*=\\s*");

			if (kv.length > 0) {
				final String key = kv[0].trim();

				final StringBuilder vb = new StringBuilder();

				String value = null;

				if (kv.length > 1) {
					vb.append(kv[1]);

					for (int i = 2; i < kv.length; i++) {
						vb.append('=');
						vb.append(kv[i]);
					}

					value = vb.toString();
				}

				set(key, value);
			}
		}
	}

	public void set(final String key, final String value) {
		cookies.put(key, value);
	}

	/**
	 * @param accept
	 *            the accept to set
	 */
	public void setAccept(final boolean accept) {
		this.accept = accept;
	}
}
