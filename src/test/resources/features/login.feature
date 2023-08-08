Feature: Login

  Scenario: Login success
    Given I am seeing the login form
    And I enter an existing email into the secure form
    Then I enter a valid password into the secure form
    And I am logged in

  Scenario Outline: Login failure
    Given I am seeing the login form
    And I enter an existing email into the secure form
    And I enter a "<Password>"
    And I am checking the "<ErrorMessage>"
    Examples:
    |Password| ErrorMessage||
    |12345        |  Please enter your password.||
    |        |  Please enter your password.||
    |!@#$%        |  Please enter your password.||
    |123456        |  Whoops! Unable to log in. Please try again or reset password.||
    |qwertyuiopasdfghjklzxcvbnm        |  Whoops! Unable to log in. Please try again or reset password.||








