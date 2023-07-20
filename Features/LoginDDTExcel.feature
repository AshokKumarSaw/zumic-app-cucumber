Feature: Login Data Driven with Excel

  @sanity
  Scenario Outline: Login Data Driven Excel
    Given User Launch browser
    And opens URL "https://zumic.com/admin/account/login"
    Then check that user navigates to MyAccount Page by passing username and password with excel row "<row_index>"

    Examples: 
      | row_index |
      |         1 |
      |         2 |
      |         3 |
      |         4 |
      |         5 |
