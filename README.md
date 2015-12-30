Vastra
=============

Vastra is a library that helps to validate objects applying different strategy validations.

## How To

*For a working implementation of this project see the `sample/` folder.*

### Creating strategies

Create the strategy instances that you want to validate your objects:

```java

// Default validation strategy (always return validity)
DefaultValidationStrategy defaultStrategy = new DefaultValidationStrategy();

// Timestamp based validation strategy
TimestampValidationStrategy timestampStrategy = new TimestampValidationStrategy();

// Reachability validation strategy
ReachabilityValidationStrategy reachabilityStrategy = new ReachabilityValidationStrategy(getApplicationContext());
```

Custom strategies can also be created. To do it:

1. Subclass the `ValidationStrategy` class

2. Override the `isValid(T object)` method and implement your custom logic. The method must return a value from the `ValidationStrategyResult` enum:
  - `VALID` if the object is valid.
  - `INVALID` if the object is invalid.
  - `UNKNOWN` if the strategy cannot decide.


3. If your strategy needs any particular data to implement the validation strategy, create a interface that extends of `ValidationStrategyDataSource` and make your objects implement it. Checkout the implementation of `TimestampValidationStrategyDataSource` for a better example.

### Creating the validation service

Create a `ValidationService` instance including the strategies created above.

```java
List<ValidationStrategy> strategies = Arrays.asList(reachabilityValidationStrategy, timestampValidationStrategy);
            
Vastra vastra = Vastra.with(strategies).build();

ValidationService validationService = vastra.validationService();
```

The order of the strategies in the array will define the order of the validation phases. Objects will attempt to validate first to the first strategy, then the second, and so on.

If none strategy validates or invalidates the object, the validation service will consider the object as invalid.


In the example above we are using the showed order because we want our validation service to work as:

1. If no internet connection available, we want our objects to be considered valid. Otherwise, lets move to the second validation strategy.
2. If the object to be validated has a `lastUpdate` date smaller than `now+expiryTime`, then lets consider the object valid. Otherwise invalid.
3. In case the object doesn't have a `lastUpdate` date, lets consider the object valid.

### Using the validation service

Lets say we have a `User` class:

```java
public class User implements TimestampValidationStrategyDataSource {

  private String name;

  private Date lastUpdate;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  @Override public Date lastUpdate() {
    return lastUpdate;
  }

  @Override public long expiryTime() {
    return 5000; // 5 seconds
  }

  @Override public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", lastUpdate=" + lastUpdate +
        '}';
  }
}
```

To validate a user instance we would do:

```java
// Example of user object that is valid 5 seconds
final User user = new User();
user.setId(1);
user.setLastUpdate(new Date(System.currentTimeMillis()));

// Check if the user is valid
boolean isValid = validationService.isValid(user); // == true

new Handler().postDelayed(new Runnable() {
  @Override public void run() {
    // Check if the user is valid after 6 seconds
    boolean isValid = validationService.isValid(user); // == false
  }
}, 6000 /*6 seconds*/);
```

Assuming we have a valid internet connection, this user would pass the validation test. However, if we run this code 5 seconds later, this same user would fail validation as the timestamp strategy would invalidate it.

Add it to your project
-------------------------------

Add Vastra dependency to your build.gradle file

```xml
dependencies {
  compile 'com.github.mobilejazz:vastra-android:1.0.2'
}
```

Do you want to contribute?
------------

Feel free to add any useful feature to the library, we will be glad to improve it :)

License
-------

    Copyright 2015 Mobile Jazz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
