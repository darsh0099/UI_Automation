Feature: Practice Test Login

  Scenario: Successful login and screenshot
    Given user opens browser
    When user navigates to the login page
    And user enters username "student"
    And user enters password "Password123"
    And user clicks login button
    Then user should be logged in successfully
    And user takes a screenshot of successful login