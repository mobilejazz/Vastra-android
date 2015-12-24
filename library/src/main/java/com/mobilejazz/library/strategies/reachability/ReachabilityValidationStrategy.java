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

package com.mobilejazz.library.strategies.reachability;

import android.content.Context;
import android.net.ConnectivityManager;
import com.mobilejazz.library.ValidationStrategyDataSource;
import com.mobilejazz.library.strategies.ValidationStrategy;
import com.mobilejazz.library.strategies.ValidationStrategyResult;

public class ReachabilityValidationStrategy implements ValidationStrategy {

  private Context context;

  public ReachabilityValidationStrategy(Context context) {
    this.context = context;
  }

  @Override
  public <T extends ValidationStrategyDataSource> ValidationStrategyResult isValid(T object) {
    final ConnectivityManager connectivityManager =
        ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

    boolean isConnected = connectivityManager.getActiveNetworkInfo() != null
        && connectivityManager.getActiveNetworkInfo().isConnected();

    return isConnected ? ValidationStrategyResult.UNKNOWN : ValidationStrategyResult.VALID;
  }
}
