/*
 * Copyright 2002-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.test.context.aot.samples.hints;

import org.junit.jupiter.api.Test;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.annotation.Reflective;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.test.context.aot.samples.hints.DeclarativeRuntimeHintsSpringJupiterTests.DemoHints;
import org.springframework.test.context.aot.samples.hints.DeclarativeRuntimeHintsSpringJupiterTests.SampleClassWithGetter;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Sam Brannen
 * @since 6.0
 */
@SpringJUnitConfig
@Reflective
@RegisterReflectionForBinding(SampleClassWithGetter.class)
@ImportRuntimeHints(DemoHints.class)
public class DeclarativeRuntimeHintsSpringJupiterTests {

	@Test
	void test(@Autowired String foo) {
		assertThat(foo).isEqualTo("bar");
	}


	@Configuration(proxyBeanMethods = false)
	static class Config {

		@Bean
		String foo() {
			return "bar";
		}
	}

	static class DemoHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			hints.resources().registerPattern("org/example/config/*.txt");
		}

	}

	public static class SampleClassWithGetter {

		public String getName() {
			return null;
		}
	}

}
