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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Product implements Serializable {
	private static final long serialVersionUID = -9018404047883073841L;
	private Map<Long, Integer> data;
	private String name = "moisés";
	private Product old;
	private List<Product> users;

	public Product() {

	}

	public Product(final String name) {
		this.name = name;
	}

	public Map<Long, Integer> getData() {
		return data;
	}

	public String getName() {
		return name;
	}

	public Product getOld() {
		return old;
	}

	public List<Product> getUsers() {
		return users;
	}

	public void setData(final Map<Long, Integer> data) {
		this.data = data;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOld(final Product old) {
		this.old = old;
	}

	public void setUsers(final List<Product> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
