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

package com.mobilejazz.library;

import com.mobilejazz.library.internal.helper.Preconditions;
import com.mobilejazz.library.strategies.ValidationStrategy;
import java.util.List;

public class Vastra {

  private final ValidationService validationService;

  private Vastra(List<ValidationStrategy> strategies) {
    this.validationService = new ValidationServiceManager();
    this.validationService.initialize(strategies);
  }

  public static Builder with(List<ValidationStrategy> strategies) {
    Preconditions.checkNotNull(strategies, "strategies == null");
    return new Builder(strategies);
  }

  public ValidationService validationService() {
    Preconditions.checkNotNull(validationService,
        "validationService == null! Create one using Builder instance!");
    return validationService;
  }

  public static final class Builder {
    private List<ValidationStrategy> strategies;

    public Builder(List<ValidationStrategy> strategies) {
      this.strategies = strategies;
    }

    public Vastra build() {
      return new Vastra(strategies);
    }
  }
}
