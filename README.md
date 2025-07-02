# Shipping Profit of Loss Calculation API

Documentation -> http://localhost:8080/swagger-ui/index.html

Database -> http://localhost:8080/h2

 - `data.sql` file contains sample data for testing.

Endpoints:
- `POST /financial`: Calculate profit or loss for a shipping transaction.

Request Body:
```json
{
  "shipmentID": "0001",
  "income" : {
    "value": 200.5
  },
  "costs" : {
    "cost": 300,
    "additionalCost": 10
  }
}
```
- `GET /financial/{shipmentID}`: Retrieve profit or loss history for a specific shipment ID.
Response:
```json
[
  {
    "Income": 200.5,
    "Total Costs": 110.0,
    "Profit or Loss": 90.5
  },
  {
    "Income": 200.5,
    "Total Costs": 110.0,
    "Profit or Loss": 90.5
  }
]
```
 - Uses Cache for Shipment Profit or Loss History.
 - Unit tests are included for service,controller and repository layer.
 - Postman collection is available in the `shipments` folder.