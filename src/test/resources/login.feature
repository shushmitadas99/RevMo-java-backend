Feature: Login

  Scenario: Valid Login
    Given I am at the  login page
    When I type in a email of "jd80@a.ca"
    And I type in a password of "Password123!"
    And I click the login button
    Then I should be redirected to the student homepage