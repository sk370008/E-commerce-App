# E-commerce-App


This is a backend implementation of an e-commerce application that allows users to browse, search and purchase products. The backend is built using Spring MVC architecture, Hibernate ORM, SQL, and REST APIs.

Installation:-
Clone the repository to your local machine.<br/>
Update the database configuration in the application.properties file with your own database credentials.<br/>
Build the project using mvn clean package.<br/>
Run the application using java -jar target/ecommerce-0.0.1-SNAPSHOT.jar.<br/>


You can test the APIs using tools like Postman. Here are the endpoints available:

GET /api/products: Returns a list of all available products.<br/>
GET /api/products/{id}: Returns a specific product by ID.<br/>
POST /api/cart: Adds a product to the user's cart.<br/>
GET /api/cart: Returns the user's cart.<br/>
DELETE /api/cart/{id}: Removes a product from the user's cart.<br/>
POST /api/orders: Places an order for the items in the user's cart.<br/>


Features<br/>
The application comes with the following features:<br/>

Product management: Products can be added, updated, and deleted through the API.<br/>
Cart management: Users can add and remove items from their cart, and view the contents of their cart.<br/>
Order management: Users can place orders for the items in their cart, and view their order history.<br/>
Mailing feature: Users receive email notifications for order confirmation, shipping updates, and other relevant information.<br/>
Exception handling: Custom exception classes are implemented to provide clear and informative error messages.<br/>
Documentation: Swagger is integrated for documentation of APIs.


Technologies Used:-<br/>
Java<br/>
Spring Boot<br/>
Hibernate ORM<br/>
MySQL<br/>
Maven<br/>
Git<br/>


Contact
If you have any questions or feedback, please feel free to contact the developer:<br/>
Name: Mohammad Shoaib Akhtar<br/>
Email: sk370008@gmail.com<br/>
LinkedIn: https://www.linkedin.com/in/sk370008/
