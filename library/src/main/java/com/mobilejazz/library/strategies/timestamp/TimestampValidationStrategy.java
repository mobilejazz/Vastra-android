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

package com.mobilejazz.library.strategies.timestamp;

import com.mobilejazz.library.ValidationStrategyDataSource;
import com.mobilejazz.library.strategies.ValidationStrategy;
import com.mobilejazz.library.strategies.ValidationStrategyResult;

public class TimestampValidationStrategy implements ValidationStrategy {
  @Override
  public <T extends ValidationStrategyDataSource> ValidationStrategyResult isValid(T object) {
    TimestampValidationStrategyDataSource dataSource;

    if (object instanceof TimestampValidationStrategyDataSource) {
      dataSource = (TimestampValidationStrategyDataSource) object;
    } else {
      throw new IllegalArgumentException("object != TimestampValidationStrategyDataSource");
    }

    if (dataSource.lastUpdate() == null) {
      return ValidationStrategyResult.UNKNOWN;
    }

    //long diff = dataSource.lastUpdate().getTime() - System.currentTimeMillis();
    long diff = System.currentTimeMillis() - dataSource.lastUpdate().getTime();

    return diff < dataSource.expiryTime() ? ValidationStrategyResult.VALID
        : ValidationStrategyResult.INVALID;
  }
}
