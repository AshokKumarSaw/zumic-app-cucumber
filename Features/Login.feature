Feature: Login with Valid Credentials

@sanity @regression
  Scenario: Successful Login with Valid Credentials
    Given User Launch browser
    And opens URL "https://zumic.com/admin/account/login"
    When User enters username as "ashok" and Password as "teknotrait9"
    And Click on Login button
    Then User navigates to MyAccount Page
