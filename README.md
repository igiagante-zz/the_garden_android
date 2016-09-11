# The Garden

The garden app helps it`s user improve the process of growing plants using historical data and responding to certain alerts.  
The development of this app started when I was looking for an idea for the final project of Udacity NanoDegree. I wrote a capstone project and it was approved by Udacity’s Team. This document explains in detail how the app is designed. However, since the app has been growing constantly and the project has started being too big, I decided to split it up in two parts. The first part of the project ( implemented ) has the following functions:

* Every time an entity is created, the app checks if that entity name already exists. It cannot persist an entity with the same name.

* Create and log users.

![alt tag](http://i.imgur.com/hiljqqj.gif) ![alt tag](http://i.imgur.com/IqHxk3g.gif)

* Garden
  * One garden contains plants, irrigations and charts.
  * To start using the app, the user should create a garden.
  * A garden can not be deleted if it contains plants.
  * It can filter the plants list using the search view.
  * The humidity and temperature showed at the garden are from the weather api.

![alt tag](http://i.imgur.com/dVC44YR.gif)


