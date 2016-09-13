# The Garden

The garden app helps the user to improve the process of growing plants using historical data and responding to certain alerts.  
The development of this app started when I was looking for an idea for the final project of Udacity NanoDegree. I wrote a capstone project and it was approved by Udacity’s Team. This document explains in detail how the app is designed. However, since the app has been growing constantly and the project has started being too big, I decided to split it up in two parts. 

## The first part of the project **( implemented )** has the following functions:

* Every time an entity is created, the app checks if that entity name already exists. It cannot persist an entity with the same name.

* **Create and log users.**

| Create User                                    | Login User                                 |
| ---------------------------------------------- |:------------------------------------------:|
| ![alt tag](http://i.imgur.com/Dk642M1.gif)     | ![alt tag](http://i.imgur.com/KXzkXzd.gif) |

* **Garden**
  * One garden contains plants, irrigations and charts.
  * To start using the app, the user should create a garden.
  * A garden can not be deleted if it contains plants.
  * It can filter the plants list using the search view.
  * The humidity and temperature showed at the garden are from the weather api.

| Create Garden                                  | Edit Garden                                |
| ---------------------------------------------- |:------------------------------------------:|
| ![alt tag](http://i.imgur.com/H2sDwiX.gif)     | ![alt tag](http://i.imgur.com/ZLycu5F.gif) |

* **Plant**
 * To create a plant, the user navigates through a wizard made up of five steps to enter the main data, images, attributes, plagues and description.
 * The user can edit a plant using the wizard and save the data in any of the wizard’s steps.
 * The app validates that the user has entered at least a name for the plant.

* **Showing a plant’s data**
 * The images are shown using a carousel.
 * The flavors and plagues info are shown with images.
 * The attributes (effects, medicinal and symptoms) are shown with graphics. These values are generated randomly. In the next version, it will use the correct data.

* **Irrigation**
 * The irrigations are unique. Once created, they cannot be edited. Nevertheless, the user can reuse an existing irrigation to create a new one.
 * An irrigation is composed by a date, one dose and the amount of the dose used by each plant.
 * An irrigation’s dose specifies the nutrients that will be used, level of water and amount of Ph leveler.

* **Charts**
 * Weather humidity and temperature are shown.

* **Nutrients**
 * It can contain basic data, images and description.

* The app does not support operation without internet connection. Despite a database was implemented as repository pattern, it needs to synchronize the database with the API. This feature will be added in future versions. There should not be inconsistencies between the database and the API.

* Google Analytics and AdMob services are implemented in the app.

* Notifications are simulated, because the interface between the API and arduino has not been implemented yet. The arduino will provide the real information, which should be informed to the user.

* Widget
 * The user will see the last irrigation of the garden. When an irrigation is added, the widget is updated with this information.


## The second part of the app will implement the following functions:

* Allow the user to achieve operations without internet connection.
* Synchronize the database with the API when the internet connection is restored. 
* Create more than one plant at the time using the same properties.
* Create reports about:
 * Use of nutrients
 * Number of irrigations done by during one growing process
 * Plant’s growth
* Display of humidity and temperature measured by sensors
* Send real notifications using the real data provided by the arduino. Most of this implementation will be done on the API, which communicates with the arduino.
* Add more metrics for google analytics.
* Create free and paid flavours. The first will include advertising provided by the AdMob service, while the second will not.

### This app uses the following libraries:

* Dagger 2
* Butterknife
* Retrofit 2
* RxJava
* RxAndroid
* Realm
* Fresco
* MPAndroidChart

### The next videos show how the app works.

* [The Garden - Part One](https://www.youtube.com/watch?v=yP9bFCITRE0)

* [The Garden - Part Two](https://www.youtube.com/watch?v=QAB_sROu9fE)

* [The Garden - Part Three](https://www.youtube.com/watch?v=2uze2Hl9L-U)

