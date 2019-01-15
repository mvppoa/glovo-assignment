Feature: Courier's order searching

  Searching for orders using the endpoint `/orders/:courierId` will return
  a list of orders available for the courier.

  Scenario: Successfully returning courier's orders
    Given The groceries and couriers list located in /couriers.json and /orders.json
    When I search for the courier with id courier-faa2186e65f2
    Then It returns a list of orders to the courier

  Scenario: Do not find any order for a courier
    Given The groceries and couriers list located in /couriers.json and /orders.json
    When I search for the courier with invalid id courier-7e1552836a05
    Then It doesn't return any orders

  Scenario: Do not show vip items to courier without a box
    Given The groceries and couriers list located in /couriers.json and /orders.json
    When I search for the courier with invalid id courier-7e1552836a04
    Then It does not return any orders that contains the restriction values located in application.properties

  Scenario: Do not show all orders for couriers with bicycles
    Given The groceries and couriers list located in /couriers.json and /orders.json
    When I search for the courier with invalid id courier-9deac8a14c58
    Then It does not return any orders that contains the distances higher than 5KMs

  Scenario: Check sorting scenario for closest, vip, food and distance
    Given The groceries and couriers list located in /couriers.json and /orders.json
    When I search for the courier with invalid id courier-885bcb0620a2
    Then Returns a list of ordervms in the proper order