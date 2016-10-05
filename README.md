# Pet Picker

<a href='https://play.google.com/store/apps/details?id=com.dinosilvestro.petpicker&hl=en&utm_source=global_co&utm_medium=prtnr&utm_content=Mar2515&utm_campaign=PartBadge&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png'/></a>

- Published an app to the Play Store that leverages a REST API and Google Location Services to locate nearby pet shelters and the pets that are adoptable at each shelter.
- Implemented a content provider for data persistence, as well as Firebase analytics to track the status of picked pets.
- Utilized Material design principles and added a widget for the home screen for users to track the availability of picked pets.

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

![Phone Main Layout](http://i.imgur.com/oEe48bi.png)

![Phone Shelter List Layout](http://i.imgur.com/sWACGqQ.png)

![Phone Shelter Detail Layout](http://i.imgur.com/pOK63m9.png)

![Phone Pet Grid Layout](http://i.imgur.com/DUMaZGB.jpg)

![Phone Pet Detail Layout](http://i.imgur.com/0A84Wi3.jpg)

![Phone Pet Picks Layout](http://i.imgur.com/tbV7het.jpg)

![Phone Widget Layout](http://i.imgur.com/HEDmMvt.png)
