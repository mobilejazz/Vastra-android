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
import com.mobilejazz.vastra.strategies.timestamp.TimestampValidationStrategy;
import com.mobilejazz.vastra.strategies.timestamp.TimestampValidationStrategyDataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class TimestampValidationStrategyTest {

  private ValidationService validationService;

  public static final long SECOND = 1000; // in milli-seconds.
  public static final long HOUR = SECOND * 3600; // in milli-seconds.
  public static final long DAY = 24 * HOUR; // in milli-seconds.

  @Before public void setUp() throws Exception {
    List<ValidationStrategy> strategies =
        Arrays.asList(new TimestampValidationStrategy(), new DefaultValidationStrategy());

    Vastra vastra = Vastra.with(strategies).build();

    validationService = vastra.validationService();
  }

  @Test public void shouldRespondValidObjectWithExpiryTimeInHours() throws Exception {
    User user = fakeUserWithLastUpdate(Type.HOURS, 3);

    boolean isValid = validationService.isValid(user);

    Assertions.assertThat(isValid).isTrue();
  }

  @Test public void shouldResponseInvalidObjectWithExpiryTimeInHours() throws Exception {
    User user = fakeUserWithLastUpdate(Type.HOURS, 26);

    boolean isValid = validationService.isValid(user);

    Assertions.assertThat(isValid).isFalse();
  }

  @Test public void shouldRespondValidObjectWithExpiryTimeInSeconds() throws Exception {
    User user = fakeUserWithLastUpdate(Type.SECONDS, 3);

    boolean isValid = validationService.isValid(user);

    Assertions.assertThat(isValid).isTrue();
  }

  @Test public void shouldResponseInvalidObjectWithExpiryTimeInSeconds() throws Exception {
    User user = fakeUserWithLastUpdate(Type.SECONDS, 6);

    boolean isValid = validationService.isValid(user);

    Assertions.assertThat(isValid).isFalse();
  }

  @Test public void shouldRespondValidObjectWithExpiryTimeInDays() throws Exception {
    User user = fakeUserWithLastUpdate(Type.DAYS, 3);

    boolean isValid = validationService.isValid(user);

    Assertions.assertThat(isValid).isTrue();
  }

  @Test public void shouldResponseInvalidObjectWithExpiryTimeInDays() throws Exception {
    User user = fakeUserWithLastUpdate(Type.DAYS, 28);

    boolean isValid = validationService.isValid(user);

    Assertions.assertThat(isValid).isFalse();
  }

  //region Private methods

  private enum Type {
    SECONDS,
    HOURS,
    DAYS
  }

  private User fakeUserWithLastUpdate(Type type, int time) {
    User user = new User();
    user.setId(1);

    long expiryTime = 0;
    long lastUpdate = 0;
    switch (type) {
      case SECONDS:
        expiryTime = SECOND * 5;
        lastUpdate = System.currentTimeMillis() + time * SECOND;
        break;
      case HOURS:
        expiryTime = HOUR * 24;
        lastUpdate = System.currentTimeMillis() + time * HOUR;
        break;
      case DAYS:
        expiryTime = DAY * 24;
        lastUpdate = System.currentTimeMillis() + time * DAY;
        break;
    }
    user.setExpiryTime(expiryTime);
    user.setLastUpdate(new Date(lastUpdate));

    return user;
  }

  //endregion

  //region Inner classes

  class User implements TimestampValidationStrategyDataSource {
    public int id;
    public long expiryTime;
    public Date lastUpdate;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public void setLastUpdate(Date lastUpdate) {
      this.lastUpdate = lastUpdate;
    }

    @Override public Date lastUpdate() {
      return lastUpdate;
    }

    public void setExpiryTime(long expiryTime) {
      this.expiryTime = expiryTime;
    }

    @Override public long expiryTime() {
      return expiryTime;
    }
  }

  //endregion
}
