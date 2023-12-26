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
    * POM Design Pattern

## How to use it.
### Clone the project.
Clone this project by calling the git clone <urlToThisRepo>

### Configure your project
The framework must be setted so it can works, all the configuration should be provided in the feeder files, the feeder file is set inide the 'App.Config' file and feel free to update the path where the feeder file is.
Example:

```
 <appSettings file="LocalSettings.config"> 
```

By default the framework is using **LocalSettings.config** as a feeder file for any configuration that might be used. Add all the settings that the environment will need and avoid as much as you can hardcoding a value that the script will need.

Examples of configuration properties:

    - Data files.
    - Connection strings.
    - Source folders.

After you set the configuration file try to make copies for each environment, the idea is that in a CI/CD pipeline , the configuration file will be replaced for the proper environment file in the form: `<configurationFile>.<env>.config` so the script can use it.

### Build your script
There are some templates inside the framework so you can start from there. If you decide to create from scratch you have to:

    1. Create a new class. Depending on the purpose of the class it should be inside the Pages, Interaction or Test Case folder.
    2. If you are creating a test case you should inherit from BaseTest class, as we are using NUnit we should use 3 annotations
       [SetUp], [Test] and [TearDown] , the base class has the setup and teardown methods so you can call directly by base.SetUp()
       and base.TearDown() respectively.        
    3. Pages classes only must contain the identifiers and objects related to the pages.
    4. Interaction classes should contain all the interaction to the pages and the validations so, in the test classes only a 
       function must be called. Avoid directly validations and interaction inside the test method as much as you can unless
       the interaction will result in a single line function.

Here is the example

```csharp
[Test]
public void testMethod()
{
    MyWebPage.GetSearchButton().Click(); // This is right 

    //The following validation should be inside a function 
    string value = MywebPage.GetProperty("value");
    if (value.Equals("Expected value")){
        Console.Writeline("This should be in a function")
    }

    //The above code should be wrapped in a function
    MyWebPageInteraction.ValueValidation();
}
```
        
### Steps reporting
Is important that inside each interaction function you have to set the step with `base.SetCurrentStep("StepName")` and before leaving function evaluate or at least report the step passed with `base.PassStep()` or `base.FailStep(exception)` if anything causes an unhandled exception the tear down will handle the 
exception as an a fail step so if you are expecting an exception be aware that you need to catch or handle the exception so the tear down will not handle it.

### StandAlone VS Parallel Execution.
Inside the templates you will find example for parallel execution.

### Special considerations for parallel execution
Step 1: Declare the following methods in your script , as Nunit creates one thread for each test case the script should use `BaseClassManager` to handle them properly
```csharp
[Parallelizable(ParallelScope.Children)]
class ParallelTest : BaseTest
    {
        [OneTimeSetUp]
        public void BeforeThisClass() { base.OneTimeSetUp(); }

        [SetUp]
        public void BeforeEachTest() 
        {
            BaseTest _base = BaseClassManager.Add(Thread.CurrentThread.ManagedThreadId);
            _base.SetUp(true);
        }
    
    [TearDown]
        public void AfterEachTest() 
        {
            BaseTest _base = BaseClassManager.Get(Thread.CurrentThread.ManagedThreadId);
            _base.TearDown(true); 
        }

        [OneTimeTearDown]
        public void AfterAllClass() { base.OneTimeTearDown(); }
    }
```
Create your own test as usual.

### Cross browser support.
To enable cross browser execution you have to pass the browser type in the `[TestCase]` annotation, then declare a paramater of BrowserType and evaluates it at the beginning of the test method , then pass it to the `base.SetUp(true,browser)` method so the browser taken from the configuration will be closed and the new browser will be launched. See the example below that executes the test for Chrome, Firefox, Edge and Internet Explorer in parallel:

```csharp
    [TestCase(BrowserType.Chrome)]
        [TestCase(BrowserType.Firefox)]
        [TestCase(BrowserType.Edge)]
        [TestCase(BrowserType.InternetExplorer)]
    public void DuckDuckSearchSomethingToFail(BrowserType browser = BrowserType.FromConfiguration)
    {
        BaseTest _base = BaseClassManager.Get(Thread.CurrentThread.ManagedThreadId);
        if (!browser.Equals(BrowserType.FromConfiguration))
            {                
                _base.SetUp(true,browser);
            }
    }
```
### Helpers
There are a few helpers inside the framework:

    * Assert Helper: Contains all the possible validations that you might use.
    * CommonFunctions: Contains function like GetTimeStamp(), GetBinFolder(), etc.
    * Control Helper: Contains all interaction's support methods like click with JS , etc.
    * Utils (static class): Contains all functions that you might need like sending something to the log with Utils.Printline("") ;
    * Dao (Data access object): An interface that any data access should implement, you can find examples for Excel and SQL Server.

### Report
Framework is using Extent Reports to create an html to inform the execution results also and email is sent if you set properly the email configuration.
