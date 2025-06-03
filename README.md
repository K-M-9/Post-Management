<h1 align="center">
Post Management App
 </h1>

<p align="center">📱 Users can fetch posts from the internet, mark them as favorites, add local comments, and create, edit, or delete their own posts. A search and filter system lets them view favorite posts or only their own content. On app launch, a welcome notification appears, and the interface automatically supports dark and light modes.</p>





## Features ✨
- Fetch Internet Posts
  - Retrieve and display posts from a remote API.
- Favorite & Comment
  - Mark any fetched post as a favorite and add local comments to it.
- Create, Update & Delete Local Posts
  - Users can write, edit, and delete their own posts stored on the device.
- Search & Filter
  - Search all posts by keyword
  - Filter to show only favorite posts
  - Filter to show only user-created posts
  - When a filter is applied, search operates only within the filtered set
- Offline Cache
  - Store fetched and user-created posts locally so they can be viewed without an internet connection.
- Welcome Notification
  - Display a welcome message notification when the app is opened.
- Dark Mode & Light Mode
  - Support for both themes, switching automatically based on system settings or user preference.

## Preview 📱

https://github.com/user-attachments/assets/447cc135-429a-4613-8b17-fe869fc490c8


## Screenshots 📱

<img width="150" src="https://github.com/user-attachments/assets/6689b538-4aab-4601-96ec-1e230530da0d" alt="Image 14">
<img width="150" src="https://github.com/user-attachments/assets/bbc5db0f-9e2e-4ceb-8440-470abf50a0ca" alt="Image 1">
<img width="150" src="https://github.com/user-attachments/assets/c86152a1-a31e-4fb5-ada2-7648cdde6987" alt="Image 2">
<img width="150" src="https://github.com/user-attachments/assets/d40cfc1f-3c02-48b3-bf5b-ce5b44d2d6a3" alt="Image 3">
<img width="150" src="https://github.com/user-attachments/assets/9965631f-0337-431c-9ee1-546a900921ad" alt="Image 4">
<img width="150" src="https://github.com/user-attachments/assets/67a2556b-a08f-4904-b84a-0ce8314fd1ed" alt="Image 5">
<img width="150" src="https://github.com/user-attachments/assets/f6187f61-c843-40c6-af47-be6389cb7a81" alt="Image 6">
<img width="150" src="https://github.com/user-attachments/assets/c0c06d0e-c94f-4675-9cd0-9d9f6a148e86" alt="Image 7">
<img width="150" src="https://github.com/user-attachments/assets/bc025526-530f-4bcb-a907-42bb26d32a34" alt="Image 8">
<img width="150" src="https://github.com/user-attachments/assets/72c40f22-8deb-44a7-b4b0-ea6f5b5167f8" alt="Image 9">
<img width="150" src="https://github.com/user-attachments/assets/e501f8dc-df6f-4e71-96ef-e50c0f8662da" alt="Image 10">
<img width="150" src="https://github.com/user-attachments/assets/0c3c35fa-59f0-4a2b-b3ee-b60bef7f5962" alt="Image 11">
<img width="150" src="https://github.com/user-attachments/assets/1c279270-1e43-46e4-8a4d-166890cba8f5" alt="Image 12">
<img width="150" src="https://github.com/user-attachments/assets/a12ca575-01e7-4a50-b931-d8eeea62eb87" alt="Image 13">
<img width="150" src="https://github.com/user-attachments/assets/fbf3402b-c314-4ce2-aea2-3a7409261ecb" alt="Image 15">
<img width="150" src="https://github.com/user-attachments/assets/75ea03ea-f0f4-41ea-bcdd-2ef9ad76575c" alt="Image 16">
<img width="150" src="https://github.com/user-attachments/assets/c0750ddc-f63a-46a5-b1b9-adeb6f62f461" alt="Image 17">
<img width="150" src="https://github.com/user-attachments/assets/295665c0-2a4a-42cc-bc8c-9358dd7218bd" alt="Image 18">
<img width="150" src="https://github.com/user-attachments/assets/fa53d1d4-2678-41f4-bc67-920d78823d67" alt="Image 19">
<img width="150" src="https://github.com/user-attachments/assets/a8714df3-b29a-4cdd-beef-ecca00d5bcc6" alt="Image 20">


## Techniques 🛠
- Android (Kotlin): Core platform and language for building the app.
- Gradle (AGP) & KSP: Build system managing dependencies and Kotlin Symbol Processing for annotation processing.
- SplashScreen API: Provides a native launch screen experience.
- Navigation Component and Safe Args.
- Paging 3: Manages loading data in pages for RecyclerView.
- Room: Local database.
- Architecture: MVVM with Clean Architecture
- Data Binding: Binds UI components in layouts to data sources in your app, reducing boilerplate code.  
- Retrofit & Gson Converter: Simplifies HTTP requests and JSON parsing for network operations.
- Hilt (Dagger): Dependency injection framework to provide and manage app-wide dependencies.
- kotlinx-coroutines (Android & Test): Asynchronous programming and structured concurrency, plus test helpers.
- Lifecycle Runtime & ViewModel KTX: Lifecycle-aware components and ViewModel support for UI data.
- SDP / SSP: Scalable size units for consistent UI sizing across different screen densities.
- Testing Frameworks:JUnit (unit testing) , MockK (mocking and stubbing for Kotlin) , kotlinx-coroutines-test (testing coroutines)
