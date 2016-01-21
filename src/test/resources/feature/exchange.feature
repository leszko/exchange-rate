Feature: Exchange rates

  Scenario: User not logged in
    When I go to the exchange page
    Then Page login is displayed

  Scenario: Login to exchange page
    Given User with name: 'test' and password: 'test123' is registered
    When I go to the exchange page
    When I enter name: test
    When I enter password: test123
    When I click submit
    Then Page exchange is displayed

  Scenario: Live exchange rate
    Given User is logged in
    Given live USD exchange rate is 1.12
    When I go to the exchange page
    When I enter currency: USD
    When I click submit
    Then Page contains text '1.12'

  Scenario: Historical exchange rate
    Given User is logged in
    Given historical USD rate for date 2010-10-06 is 1.45
    Given historical EUR rate for date 2011-12-06 is 5.43
    Given historical PLN rate for date 2012-11-03 is 7.45
    When I go to the exchange page
    When I enter currency: USD
    When I enter date: 2010-10-06
    When I click submit
    When I enter currency: EUR
    When I enter date: 2011-12-06
    When I click submit
    When I enter currency: PLN
    When I enter date: 2012-11-03
    When I click submit
    Then Page contains text '1.45'
    Then Page contains text '5.43'
    Then Page contains text '7.45'


