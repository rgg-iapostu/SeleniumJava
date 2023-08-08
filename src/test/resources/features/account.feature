Feature: Account

  Scenario Outline: Update Member Info
    Given I am seeing the login form
    And I enter an existing email into the secure form
    Then I enter a valid password into the secure form
    And I am logged in
    And I update my "<First Name>" and "<Last Name>"
    And I see the Account Updated message
    Examples:
      | First Name | Last Name  |
      | !@#$%^&    | @#$%^&     |
