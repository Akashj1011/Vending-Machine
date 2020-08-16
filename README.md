# Vending-Machine

# Classes 
*Main.class : It is the starting point of the application. Need to run this class to run the application.

*VeningMachineService: It is like a gateway between Main class and Vending Machine class, it decides which methods of Vending Machine to be called.

*VendingMachine: It is the interface specifying all the methods that Vending Machine need to execute.

*VendingMachineImpl: It implements the VendingMachine class and implements all the methods of VendingMachine.

*Constants: It has all the constants used in the application.

*InputJsonDataLoader: It loads the data from the input json file.

*BeverageMaker: It executes all the methods needed to prepare a beverage.

*input.json: Input file in resource folder.

# Flow 
1. Starts loading data from the input file.

2. Creates VendingMachine Object having number of outlets specified in input file.

3. Adds ingredient in the Vending Machine specified in input file.

4. Starts preparing all the beverages specified in input file.

# Design
-VendingMachine interface specifies all the methods that needs to be implemented by VendingMachine. It has methods like addNewIngredient(), updateExistingIngredientQuantity(), prepareBeverage(), removeIngredient(), shutdown().

-VendingMachineImpl implements all the methods specified by VendingMachine. It has ExecutorService Threadpool which is responsible for preparing all the beverages parallely. The no of threads in threadpool is the no of outlets of VendingMachine. It has attributes like outlets and currentIngredientStock. Both these variables are static because the ingredient stock in Vending Machine and outlets will be common and same for all the Beverages.

-Executor service executes called BeverageMaker Object. BeverageMaker class implements runnable which is responsible for making the beverage, so executor service has multiple threads each responsible for making beverage by executing Beverage Object of each beverage.

-While making a beverage we first check if the ingredients used to prepare it is available and in sufficent quantity or not and then only we procees further to prepare beverage. While preparing beverage to get existing ingredients stock we are using synchronized block for accessing currentIngredientStock because it is a critical variable.

# Assumptions:
-This solution is for single Multithreaded Vending Machine which can parallely prepare multiple beverages.

-All the use-cases and methods are covered using unit tests and as Api is not allowed We are reading the data from input file and preparing the beverages. 