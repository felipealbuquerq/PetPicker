# Pet Picker

<a href='https://play.google.com/store/apps/details?id=com.dinosilvestro.petpicker&hl=en&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'/></a>

- Published an app to the Play Store that leverages a REST API and Google Location Services to locate nearby pet shelters and the pets that are adoptable at each shelter.
- Implemented a content provider for data persistence, as well as Firebase analytics to track the status of picked pets.
- Utilized material design principles and fragments to add a special master-detail layout to support tablets. 
- Added a widget for the home screen for users to track the availability of picked pets, as well as the ability to share pet picks, and app shortcuts to support the latest release of Android Nougat 7.1.

## Implementations (Stage 1 - Design)

- Proposal contains an overview description.
- Proposal contains a description of the intended user.
- Proposal contains user interface mocks.
- Proposal declares the app’s primary features.
- Proposal outlines any key constraints such as data persistence, UX corner cases, and libraries used.
- Proposal describes a plan to implement the main features of the app via a set of well structured technical tasks.
- UI mocks depict interaction stories that adhere to Core App quality guidelines.

## Implementations (Stage 2 - Build)

###Common Project Requirements
- App conforms to common standards found in the Android Nanodegree General Project Guidelines

### Core Platform Development
- App integrates a third-party library.
- App validates all input from servers and users. If data does not exist or is in the wrong format, the app logs this fact and does not crash.
- App includes support for accessibility. That includes content descriptions, navigation using a D-pad, and, if applicable, non-audio versions of audio cues.
- App keeps all strings in a strings.xml file and enables RTL layout switching on all layouts.
- App provides a widget to provide relevant information to the user on the home screen.

### Google Play Services
- App integrates two or more Google services. Google service integrations can be a part of Google Play Services or Firebase.
- Each service imported in the build.gradle is used in the app.
- If Location is used, the app customizes the user’s experience by using the device's location.
- If Analytics is used, the app creates only one analytics instance. If Analytics was not used, student meets specifications.

### Material Design
- App theme extends AppCompat.
- App uses an app bar and associated toolbars.
- App uses standard and simple transitions between activities.

### Building
- App builds from a clean repository checkout with no additional configuration.
- App builds and deploys using the installRelease Gradle task.
- App is equipped with a signing configuration, and the keystore and passwords are included in the repository. Keystore is referred to by a relative path.
- All app dependencies are managed by Gradle.

### Data Persistence
- App implements a ContentProvider to access locally stored data.
- If it needs to pull or send data to/from a web service or API only once, or on a per request basis (such as a search application), app uses an IntentService to do so.
- App uses a Loader to move its data to its views.

##Installation

Simply download or fork this repository and import into Android Studio. Add a [Petfinder](https://www.petfinder.com/developers/api-key) API key to the Keys.java file within the project. 

`public static final String apiKey = ""; //Please place your Pet Finder Api Key here`

##Screenshots

![Phone Main Layout](http://i.imgur.com/zB6judZ.png)

![Phone Shelter List Layout](http://i.imgur.com/R6JLD1Z.png)

![Phone Shelter Detail Layout](http://i.imgur.com/nM6bKT2.png)

![Tablet Shelter Dual Pane](http://i.imgur.com/WzpTR1P.png)

![Phone Pet Grid Layout](http://i.imgur.com/RLDI0d8.jpg)

![Phone Pet Detail Layout](http://i.imgur.com/uYaUimL.jpg)

![Tablet Pet Dual Pane](http://i.imgur.com/KyQG9kt.jpg)

![Phone Pet Picks Layout](http://i.imgur.com/MNhovKW.jpg)

![Phone Widget Showcase](http://i.imgur.com/BJEf6tb.png)

![Phone Quick Action Showcase](http://i.imgur.com/zmCVIsX.png)

![Phone Quick Action Icon Showcase](http://i.imgur.com/07lNrLL.png)

## License

Copyright 2017 Dino Silvestro

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.




