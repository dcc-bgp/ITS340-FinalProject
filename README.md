# ITS340_Java_Final_Project
Our idea is to create an application for a music store that manages inventory,  employees, and customer data for multiple music storelocations. The progra will allow employees to seamlessly search the database, manage physical media stock (Vinyl and CDs), and process customer shopping carts through an intuitive graphical user interface.

## Prerequistes
- Java Development Kit (JDK): Version 8 or higher.
- Database: MySQL Workbench (Running on port 3307).

# How to Run the Project
1. Database Setup: Open MySQL Workbench.
    - Execute the MusicStoreDB_MasterReset.sql script to build the schema and populate the test data.

2. Configure Connection:
    - Open src/db/DatabaseConnection.java.
    - Ensure the url points to port 3307.
    - Update the user and password variables to match your local MySQL server credentials.

3. Compile and Execute:
    - Open your terminal and navigate to the src folder:
```Bash
cd src
```
    - Compile the Java files, linking the MySQL driver:
```Bash
javac -cp ".;mysql-connector-j-9.6.0.jar" Main.java
```
    - Run the compiled application:
```Bash
java -cp ".;mysql-connector-j-9.6.0.jar" Main
```

# Core Requirements and Implementation
1. Two Non-Trivial Data Structures
    - Queue (via LinkedList): Used to implement the "Shopping Cart" checkout feature. As employees add albums to a transaction, they are queued. The processCheckout() method utilizes standard First-In, First-Out (FIFO) logic, using .poll() to process and remove items sequentially.
    - ArrayList: Used within the Data Access Object (DAO) and Controller to dynamically collect, store, and iterate through search results fetched from the MySQL database before pushing them to the GUI table model.
2. GUI using Java Swing
    - Has a primary JFrame containing multiple interactive elements: JTextFields for data entry, JButtons to trigger CRUD actions, and a scrollable JTable to display real-time inventory updates.
3. Three Design Patterns
    - Model-View-Controller (MVC): The codebase is strictly divided into packages. The model holds data objects (Album, ShoppingCart), the view handles the Swing UI, and the controller manages the event listeners and application flow between the UI and the database.
    - Factory Method Pattern: Instead of a standard object constructor, the AlbumFactory.java is used to instantiate different types of physical media. Based on the Format string pulled from the database, the factory dynamically creates and returns either a Vinyl or CD subclass object.
    - Singleton Pattern: The DatabaseConnection.java class utilizes a private constructor and a public getInstance() method. This ensures that the application only ever opens a single, efficient connection to the MySQL server during its runtime.
4. MySQL Database Integration
    - CRUD Functionality: Utilizing the AlbumDAO.java class and JDBC PreparedStatements, the application actively performs all four CRUD operations: