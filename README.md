Vastra
=============

Vastra is a library that helps to validate objects applying different strategy validations.

Usage
-----

*For a working implementation of this project see the `sample/` folder.*

1. Create the strategy instances that you want to validate your objects.

    ```java

    ReachabilityValidationStrategy reachabilityValidationStrategy = new ReachabilityValidationStrategy(getApplicationContext());

    TimestampValidationStrategy  timestampValidationStrategy = new TimestampValidationStrategy();
    
    List<ValidationStrategy> strategies = Arrays.asList(reachabilityValidationStrategy,
                timestampValidationStrategy);
    ```

2. Create the ValidationService object to start to validate the objects

    ```java
    Vastra vastra = Vastra.with(strategies).build();
    ValidationService validationService = vastra.validationService();
    ```

3. ValidationService instance contains the isValid(T object) method to make the validations.

    ```java
    // Example of user object that is valid 5 seconds
    final User user = new User();
    user.setId(1);
    user.setName("Jose Luis Franconetti Olmedo");
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

Add it to your project
-------------------------------

Add Vastra dependency to your build.gradle file

```xml
dependencies {
  compile 'com.github.mobilejazz:vastra-android:1.0'
}
```

Do you want to contribute?
------------

Feel free to add any useful feature to the library, we will be glad to improve it :)

License
-------

    Copyright 2015 Mobile Jazz SL

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
