# Yap
It's a Yelp-like review site, called Yap! build upon a two-tier web application with a Java front end and a SQL backend. MySQL server has been used as a backend for storing the data in the relational database. 
This website is developed with the help of jetty web-server. It uses servlets to provide the dynamic web-page to the user. Bootstrap/ HTML and Javascript has been used as a front-end side for providing the presentation layer to the User. All the user/ business/ review information has been stored in the relational database using MySQL as a backend.

1. Table Structures/ Primary Key for Storing the User, Business and Review Information has been provided in the folder: TableStructure_MySQL.
No other third party installations are required to start using the web-site if you have jdk1.8 (with all neccessary JAR files such as : org.eclipse.jetty.server.Server, org.eclipse.jetty.servlet.ServletContextHanlder)and MySQL5.1 installed in your system. Create the neccessary tables as described in the text file under the folder specified in MySQL.

2. Package: cs601.LoadDatabase contains the 3 java files which actually stores the dummy information in the tables created in MySQL database. You can modify the arrays in the files based on your need or you can upload the data into the tables from a JSON/ CSV or any text file by using the same programs or using MySQL commmands.

3. Once all neccessary set-up has been done as discussed above, you can just start the server "YapServer" which is under the package: cs601.YapServlet.PageHandlers by passing the server-port in the command line arguments. 
For example: java -jar YapServer 10000, where 10000 is the <Port>

The web-server has been started now and you can get the web-page by typing the url: http://<your_machine_node>:<server_port>/yap. 
For example: http://localhost:10000/yap

Sample look of the Yap web-page has been provided in the web-output folder

NOTE: Make sure you change the MySQL Connection parameter accordingly to get the DB connection.
