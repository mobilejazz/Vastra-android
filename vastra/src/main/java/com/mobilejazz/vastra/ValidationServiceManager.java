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

package com.mobilejazz.vastra;

import com.mobilejazz.vastra.strategies.ValidationStrategy;
import com.mobilejazz.vastra.strategies.ValidationStrategyResult;
import java.util.List;

public class ValidationServiceManager implements ValidationService {

  private List<ValidationStrategy> strategies;

  public ValidationServiceManager() {
  }

  @Override public void initialize(List<ValidationStrategy> strategies) {
    if (strategies == null || strategies.size() <= 0) {
      throw new IllegalArgumentException("strategies == null || strategies.size() <= 0");
    }

    this.strategies = strategies;
  }

  @Override public <T extends ValidationStrategyDataSource> boolean isValid(T object) {
    if (object == null) {
      return false;
    }

    // Default value set to false;
    boolean isValid = false;

    for (ValidationStrategy strategy : strategies) {
      ValidationStrategyResult result = strategy.isValid(object);

      if (result == ValidationStrategyResult.VALID) {
        isValid = true;
        break;
      } else if (result == ValidationStrategyResult.INVALID) {
        isValid = false;
        break;
      } else {
        // result is ValidationStrategyResult.UNKNOWN
        // lets iterate to next strategy
      }
    }

    return isValid;
  }
}
