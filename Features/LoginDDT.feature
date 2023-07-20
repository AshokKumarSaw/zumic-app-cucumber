Feature: Login Data Driven
@regression
  Scenario Outline: Login Data Driven
    Given User Launch browser
    And opens URL "https://zumic.com/admin/account/login"
    When User enters username as "<username>" and Password as "<password>"
    And Click on Login button
    Then User navigates to MyAccount Page

    Examples: 
      | username | password    |
      | ashok    | teknotrait9 |
      | brad     | zumic9      |
