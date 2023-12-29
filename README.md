I didn't add a gitignore because I wanted to show the example of the reports that I integrated

The project is running on the java version 21.0.1

# Code Challenges - Automation Test
In this readme file you will find:

    * How the framework was built.
    * How to use it.    

## Technologies involved in the framework.
    * Java
    * Selenium WebDriver
    * Extent Reports
    * Maven
    * POM Design Pattern

## How to use it.
### Clone the project.
Clone this project by calling the git clone <urlToThisRepo>

### Choose a TC
There are 4 different classes on the "src/test/java/demo" folder, each one representing a TC, two for each challenge given, because the instructions given were giving me trouble to follow because there were times the page that popped up was on another page and it would fail if I follow the instructions exactly as portrayed on the challenge documents, that's why I named the classes "SantanderTestCase###Exact" (the ### representing the TC number)  for the TC with the exact instructions given by the document, which will fail and "SantanderTestCase###Suggestion" for the TC that was designed and planned by me to fulfill the purpose of the test, which are the ones that are going to work and give the outcome expected as part of the instructions.

To run the TC you just have to open the class with the name representing the TC you wish to evaluate and then run that class. All those classes have a main method so it should be enough with those steps.

I was planning on using JUnit for the TC but I thought it was better to keep it simple to show the programming and front end basics on the code.
        

### Report
All TC's will fill a report using Extent Reports when executed that can be found on the "src/test/resources/reports" folder with the name "SparksReport.html".
