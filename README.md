# News-App-Demo

## :scroll: Description
News-App-Demo is sample project of news app using open source api newsapi.org

### App Architecture
News-App-Demo is implements MVVM architecture using Koin, Retrofit and Kotlin coroutines. 

### Libraries

[Gson](https://code.google.com/p/google-gson/) Gson library provides a powerful framework for converting between JSON strings and Java objects. 

**Networking and caching.** There are a couple of battle-proven solutions for performing requests to backend servers, which you should use rather than implementing your own client. Here used [OkHttp](http://square.github.io/okhttp/) for efficient HTTP requests and using [Retrofit](http://square.github.io/retrofit/) to provide a typesafe layer. 

[Glide](https://github.com/bumptech/glide) is use for loading and caching images.

[Koin](https://github.com/InsertKoinIO/koin) Koin is a lightweight dependency injection framework for Kotlin. Koin is a DSL, a light container and a pragmatic API

[Kotlin coroutines](https://developer.android.com/kotlin/coroutines) coroutine is used for concurrency design pattern that use to simplify code, that executes asynchronously.

## :camera_flash: Preview
<img src="/gif/news-app-demo.gif" width="300">

#### The app has following packages:
1. **data**: It contains all the data accessing and manipulating components.
2. **di**: Dependency providing classes using Kon.
3. **ui**: View classes along with their corresponding ViewModel.
3. **api**: It contains all the Retrofit related classes along with base api calling repository.
4. **utils**: Utility classes.