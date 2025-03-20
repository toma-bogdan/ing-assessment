# Prerequisites

In order to be able to submit your assignement, you should have the following installed:
* JDK >= 17
* Maven
* Postman / any other tool that allows you to hit the application's endpoints
* Any versioning tool
* Any IDE that allows you to run the application


# How to

#### Run the application
The application should be run as a SpringBootApplication. Below is a quick guide on how to do that via IntelliJ:
* Edit Configuration 
   * Add New Configuration (Spring Boot)
     * Change the **Main class** to **ing.assessment.INGAssessment**
       * Run the app.

#### Connect to the H2 database
Access the following url: **http://localhost:8080/h2-console/**
 * **Driver Class**: _**org.h2.Driver**_
 * **JDBC URL**: _**jdbc:h2:mem:testdb**_
 * **User Name**: _**sa**_
 * **Password**: **_leave empty_**

#### Implementation

1. **Access the Endpoint**

    - **Placing an order**:
    * Method: POST
    * URL: `localhost:8080/orders`
    * Body:
      ```json
      [
          {
            "productCK": {
              "id": 1,
              "location": "MUNCHEN"
            },
            "quantity": 3
          }
      ]
      ```  

---

# Features

### Ordering System
- **Order Placement**
   Users can create orders by specifying product id it's location and quantity.

- **Stock Validation**
  Verifies if the requested product is in the database, and it's quantity is available.

- **Compute discounts**
  Automatically applies discounts to orders exceeding the configured thresholds.
- 
- **Delivery Cost Management**
  Using java streams, all product locations are being mapped and for each distinct one,
  2 days are added to delivery time

- **Concurrent Updates**
 Supports optimistic locking preventing race conditions on accessing product quantities

- **Error Handling**
- Returns appropriate HTTP status codes for invalid requests.
- Provides user-friendly error messages for issues such as invalid product IDs, insufficient stock, or malformed requests.


