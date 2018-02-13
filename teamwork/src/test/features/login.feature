Feature: Test Scenario for Jenkins

  Scenario: Verify user login
    Given I navigated to home
    When I enter login id
    And I enter user name
    And I tap on login button
    Then I should be navigated to home page