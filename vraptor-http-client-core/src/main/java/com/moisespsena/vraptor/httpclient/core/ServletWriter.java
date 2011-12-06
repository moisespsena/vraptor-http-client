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
package com.moisespsena.vraptor.httpclient.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class provides a simple method of posting multiple Serialized objects to
 * a Java servlet and getting objects in return. This code was inspired by code
 * samples from the book 'Java Servlet Programming' by Jason Hunter and William
 * Crawford (O'Reilly & Associates. 1998).
 */
public class ServletWriter {

	static public ObjectInputStream postObjects(final URL servlet,
			final Serializable objs[]) {
		URLConnection con;
		try {
			con = servlet.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// Write the arguments as post data
			final ObjectOutputStream out = new ObjectOutputStream(
					con.getOutputStream());
			final int numObjects = objs.length;
			for (int x = 0; x < numObjects; x++) {
				out.writeObject(objs[x]);
			}

			out.flush();
			out.close();

			return new ObjectInputStream(con.getInputStream());
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}