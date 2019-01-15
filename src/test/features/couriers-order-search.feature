Feature: Courier's order searching

  Searching for orders using the endpoint `/orders/:courierId` will return
  a list of orders available for the courier.

  Scenario: Successfully returning courier's orders.
    Given The groceries and couriers list located in /couriers.json and /orders.json
    When I search for the courier with id 'courier-7e1552836a04'
    Then It returns '2' orders for the courier

  Scenario: Do not find any order for a courier.
    Given The groceries and couriers list located in /couriers.json and /orders.json
    When I search for the courier with invalid id 'courier-7e1552836a05'
    Then It doesn't return any orders