/*
 * Copyright (C) 2015 Mobile Jazz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mobilejazz.library.strategies;

import com.mobilejazz.vastra.ValidationService;
import com.mobilejazz.vastra.Vastra;
import com.mobilejazz.vastra.strategies.DefaultValidationStrategy;
import com.mobilejazz.vastra.strategies.ValidationStrategy;
import com.mobilejazz.vastra.strategies.invalidation.InvalidationStrategy;
import com.mobilejazz.vastra.strategies.invalidation.InvalidationStrategyDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidationStrategyTest {

  private ValidationService validationService;

  @Before public void setUp() throws Exception {
    List<ValidationStrategy> strategies =
        Arrays.asList(new InvalidationStrategy(), new DefaultValidationStrategy());

    Vastra vastra = Vastra.with(strategies).build();

    validationService = vastra.validationService();
  }

  @Test public void shouldRespondValidObject() throws Exception {
    User user = new User(true /*isObjectInvalid*/);

    boolean isValid = validationService.isValid(user);

    assertThat(isValid).isFalse();
  }

  @Test public void shouldRespondInvalidObject() throws Exception {
    User user = new User(false /*isObjectInvalid*/);

    boolean isValid = validationService.isValid(user);

    assertThat(isValid).isTrue();
  }

  private class User implements InvalidationStrategyDataSource {

    private boolean isObjectInvalid;

    public User(boolean isObjectInvalid) {
      this.isObjectInvalid = isObjectInvalid;
    }

    @Override public boolean isObjectInvalid() {
      return isObjectInvalid;
    }
  }
}
