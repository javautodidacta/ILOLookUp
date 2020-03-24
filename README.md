# ILO Look Up
Tool for browsing ILO Conventions, Recommendations, GB and ILO Conferences.

[ILO site](https://www.ilo.org/global/lang--en/index.htm)

## How-to
Create a new folder (name it, i. e., _ILO_programme_).

In order to use **ILO Look Up**, you need to download the latest version of the **JDK (Java Development Kit)** and the **JavaFX** libraries:

* [JDK 13 Windows x64 Compressed Archive](https://www.oracle.com/java/technologies/javase-jdk13-downloads.html), needed to run the **jar** file.

* [JavaFX Windows SDK 13.0.2 (Latest release)](https://gluonhq.com/products/javafx/), needed to generate the user interface.

Download the zips and extract the files.

[Download **ILOLookUp.jar**](https://github.com/javautodidacta/ILOLookUp/raw/master/out/artifacts/ILOLookUp_jar/ILOLookUp.jar) and create a **.bat** file (i. e. _ILOLookUp.bat_) with the following text:
```
@echo off
.\jdk-13.0.2\bin\java.exe --module-path=.\javafx-sdk-13.0.2\lib --add-modules=javafx.controls,javafx.fxml -jar ".\ILOLookUp.jar"
```
Keep an eye on the **jdk** and **JavaFX** versions and change them accordingly.
The **jdk** and **JavaFX** folders and both the **jar** and **bat** files should be together in the folder _ILO_programme_.

**ILOLookUp** doesn't need instalation -- double click the **bat** file you just created. A black MS-Dos window will open -- **it is normal**.

## Features
### Conventions and Recommendations
**ILO Look Up** allows you to look up **ILO Conventions** or **Recommendations**. You can also download the **Convention/Recommendation** in the three available languages (Spanish, English and French) in Excel format, to compare translations or create your own translation memory.

_Known bug: there is a problem with the encoding of the file._

### What is the ILO Governing Body?

You can also browse the **Governing Body Sessions**, as well as its sections (**INS, POL, LILS and PFA**) if available.

From the [ILO website](https://www.ilo.org/gb/lang--en/index.htm):

>The Governing Body of the International Labour Office is the executive body of the International Labour Organization (the Office is the secretariat of the Organization). It meets three times a year, in March, June and November. It takes decisions on ILO policy, decides the agenda of the International Labour Conference, adopts the draft Programme and Budget of the Organization for submission to the Conference, and elects the Director-General.

### International Labour Conference Sessions

Look up information about the latest International Labour Conference in Geneva, Switzerland, or previous sessions.

>The member States of the ILO meet at the International Labour Conference, held every year in Geneva, Switzerland, in the month of June. Each member State is represented by a delegation consisting of two government delegates, an employer delegate, a worker delegate, and their respective advisers. (Employer and Worker delegates are nominated in agreement with the most representative national organizations of employers and workers.)

Read further [here](https://www.ilo.org/ilc/AbouttheILC/lang--en/index.htm).

<a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-sa/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-sa/4.0/">Creative Commons Attribution-ShareAlike 4.0 International License</a>.
