## RaceTimer

Video presentation is here: https://youtu.be/sqKbLkkqOGc

APK is here: https://drive.google.com/file/d/1AKAMnpjwdnEGIpBu9wcToyly_PJV6Ibg/view?usp=sharing

 First, a little background. There is a community of jeepers in Belarus and they periodically organize their own races. 
In October of this year, an event was held at which it was decided not to control the time of passing the route due to the complexity of control and manual calculation of the results. 
Therefore, I decided to write an application that automatically calculates the time spent on the route and forms the rating of the finishing crews.

The application was built using MVVM architecture.

During the development, I used components from the Android Jetpack library 
-  Room for storing data, 
- Kotlin flow and coroutines for transferring data and updating the UI,
-  Navigation component for switching between screens, 
-  ViewBinding for control views.

How it works?

- When application starts, user can see a start screen with two buttons: settings and a navigation to timers screen button 
- Application supports dark and light theme and runtime changeover
- On the screen with timers, user can add a new crew, also remove all participants after confirming the action.
- Participant list implemented with recycler view
- The start button becomes active at the moment the start is opened
- When the race judge marks the start, the start time appears in place of the "start" button, the finish button becomes visible and active.
- When the start closing time comes, all crews that have not started are marked as not started.
- When the race judge presses the finish button, the finish time appears instead of the finish button.
- When the finish time comes, all crews that did not arrived at the finish line are marked as not finished.
- On the results screen, user can see the list of crews sorted by the time spent on the race, then there are crews that started but didn't finish, then those who didn't show up at the start
