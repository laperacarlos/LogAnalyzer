## Running

* Build the project using command:
    ```
    mvn clean install 
    ```

* Run unit tests using command:
    ```
    mvn test 
    ```

* To run application run command passing as the argument file path to logfile.txt in format as in example below:
    ```
    java -jar target\log-analyzer.jar "C:\Development\sample_data\logfile.txt"
    ```

## How it works

* After running, the application opens Hibernate session and starts to read file line by line, analyze and save event
  log (if finished) to the database.
* If logfile.txt is formatted correctly and contains all necessary information Hibernate transaction is committed.
* If any exception has been thrown whole transaction is rolled back and no data is persisted in the database.
