WeatherGame
===========

Simple Android game where user has to guess the current temperature for a given city.

Details
=======

This application is supposed to improve your wheather understanding of the world. Different cities located in Europe and globally demand everything from your skills.

In order to succeed, you need to guess the current tempurates of those cities by using the wheeler. Click 'Enter' to compare your guess to current one prompted from the web.

There are three difficulties available. 'Easy' is comprised of European cities, whereas 'Medium' exposes you to global ones. 'Hard' only shows GPS Position. With a click on the city, a pop up with the city's country code appears.


Screenshots
==========
![Start](https://dl.dropboxusercontent.com/u/3672489/BestWeatherGame/Screenshot%20from%202013-11-18%2020%3A22%3A50.png)

![City](https://dl.dropboxusercontent.com/u/3672489/BestWeatherGame/Screenshot%20from%202013-11-18%2020%3A23%3A35.png)

![Result](https://dl.dropboxusercontent.com/u/3672489/BestWeatherGame/Screenshot%20from%202013-11-18%2020%3A24%3A09.png)


Dependencies
============

This project depend on the android-wheel library. To get the source code of it, just tpye on terminal:

`svn checkout http://android-wheel.googlecode.com/svn/trunk/ android-wheel-read-only `

In the path you made the download, you will find two folders: wheel and wheel_demo, the important one obviously is wheel, import it as project in eclipse: File-> Import-> General -> Existing Projects into Workspace and choose the folder “wheel” you just download.

![Package](https://dl.dropboxusercontent.com/u/3672489/BestWeatherGame/1.jpg)

Once we’ve imported the source code, we need to add it as library to our project.

Go to Project-> Properties -> Android, in section Library press the button Add and select the wheel project

![Library](https://dl.dropboxusercontent.com/u/3672489/BestWeatherGame/2.jpg)
