Part -1

Question 1) What is the difference between AAR and JAR?
Answer - When we want to reuse code in other applications for same purpose then we compile our code and generate a .jar or .aar file after compilation , we call this generated file as library file.
Some differences is between JAR and AAR , JAR file only include the java classes but AAR file have java classes as well as UI related files like resources, styles, xml files etc.
We can understand the AAR and JAR with below examples.
JAR file - We want to calculate or perform java classes related task then that time we only require the java files.
AAR file - 1) When we use Facebook integration in our app the Facebook provide a Login screen for user login, that UI is part of library and that UI is controlled by Facebook library.
2) Same like in Payment Gateway related libraries some predefined UI screen provided to fill the debit/credit card related data.


Question 2) What is an Intent?
Answer - Intent is a object which is responsible to open a other Android component as well as other applications.
Mostly 3 types of Intent is there-
1) Explicit intent - When we want to open other Android component in same application like Start new Activity, Start Service , Start broadcasting .For that we make an Intent object and also we can send data from 1 component to other using Intent object.
2) Implicit intent - Some time we have to use other application to perfome a perticular task for example we want to send a mail, so in this case we have to make Intent object which have the data and as well as the category of the task , on the basis of category android OS filter other applications which have the capabilitty to perform that task and OS open a group of applications in popup window then user can select the application.
3) Pending Intent - It is future related task and mostly used for Notification, when user click on the notification UI then a predefined Intent fired to start a Android component.


Question 3) What is a layout?
Answer - Layout is responsible to show a UI of the application's screen, layout have a group of views and all the views are defined in a particular structure according to your requirements.
Basically 5 layouts used in android which are LinearLayout, RelativeLayout, FrameLayout, TableLayout, ConstraintLayout.


Question 4) What is adb?
Answer - ADB is Android Debug Bridge, it is a command line tool. Using ADB we can debug our app easily , devices are be  connected via USB or WiFi.
In my current project I connect my application with android studio over WiFI by using the command "adb connect <Ip Address>", in this case system and mobile device must be connected with same network.

Question 5) What is a Fragment?
Answer - Fragment is part of activity, it is also known as sub-activity. Fragment is always attached with the Activity, without Activity fragment can not be used, so fragment lifecycle is changed according to activity lifecycle because fragments are included in activity.
There can be more than one fragment in an activity. 

Question 6) What is the difference between an activity and a service and where would you use one or the other?
Answer - Only the UI difference is there between Activity and Service, Service works on background but  run in Main thread without UI.
using Foreground Service we can perform task in backfround without active mode of app.
At a time only 1 Activity can be active but in case of Services more than 1 services can be active.
//--------------------------------------------------------------------------------------------------------------------------------------------------------

Part - 2

In this doc I have mentioned all places where I have handle the Memory Leaks.
To identify the Memory Leaks I have used LeakCenry Library in build.gradle.

1) In Subscriber.java class on line number 28 you can see that I used a WeakReference of RecyclerView.
Using this WeakReference object at run time we will not get the memory leak due to RecyclerView of Activity view.

2) In DetailFragment I used a WeakReference of ProgressBar in WebViewClient inner class.

3) I have set null value to the DataBinding object and Adapter object on onDestroy and onDestroyView() method in Activity and Fragments respectively.