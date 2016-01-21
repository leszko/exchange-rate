Feature: Register account

  Scenario Outline: Register user with valid data
    Given I go to the register page
    When I enter name: <name>
    When I enter password: <password>
    When I enter email: <email>
    When I enter birthDate: <birthDate>
    When I enter street: <street>
    When I enter zipCode: <zipCode>
    When I enter city: <city>
    When I enter country: <country>
    When I click submit
    Then User <name> should be registered

    Examples:
      | name  | password | email           | birthDate  | street        | zipCode | city   | country |
      | rafal | ala123   | rafal@rafal.com | 1987-10-02 | Mickiewicza 2 | 31-782  | Krakow | Poland  |

    
  Scenario Outline: Register user with invalid data
    Given I go to the register page
    When I enter name: <name>
    When I enter password: <password>
    When I enter email: <email>
    When I enter birthDate: <birthDate>
    When I enter street: <street>
    When I enter zipCode: <zipCode>
    When I enter city: <city>
    When I enter country: <country>
    When I click submit
    Then User <name> should not be registered

    Examples:
      | name    | password | email           | birthDate  | street        | zipCode | city   | country |
      | marysia | a        | rafal@rafal.com | 1987-10-02 | Mickiewicza 2 | 31-782  | Krakow | Poland  |
      | marysia | aafd2    | rafal           | 1987-10-02 | Mickiewicza 2 | 31-782  | Krakow | Poland  |
      | marysia | aafd2    | rafal@rafal.com | 1987-10-02 |               | 31-782  | Krakow | Poland  |




