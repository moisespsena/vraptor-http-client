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
package com.moisespsena.vraptor.httpclient.samplewebapp.components;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;
import br.com.caelum.vraptor.proxy.SuperMethod;

import com.moisespsena.vraptor.annotationscanner.AnnotationScannerResolver;

/**
 * @author Moises P. Sena (http://moisespsena.com)
 * @since 1.0 26/09/2011
 */
@Component
@ApplicationScoped
public class AnnotationScannerResolverFactory implements
		ComponentFactory<AnnotationScannerResolver> {

	private AnnotationScannerResolver instance;
	private final Proxifier proxifier;

	/**
	 * 
	 */
	public AnnotationScannerResolverFactory(final Proxifier proxifier) {
		this.proxifier = proxifier;
	}

	@Override
	public AnnotationScannerResolver getInstance() {
		return instance;
	}

	@PostConstruct
	public void initialize() {
		instance = proxifier.proxify(AnnotationScannerResolver.class,
				new MethodInvocation<AnnotationScannerResolver>() {

					@Override
					public Object intercept(
							final AnnotationScannerResolver proxy,
							final Method method, final Object[] args,
							final SuperMethod superMethod) {
						throw new UnsupportedOperationException();
					}
				});
	}

}
