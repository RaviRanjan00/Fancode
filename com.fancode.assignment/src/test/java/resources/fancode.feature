Feature: Fancode

  @Test
  Scenario: Check Users belong to Fancode city task percentage
    When I hit the API users
    Then Fetch the Users belong to Fancode city
    Then Validate Users completed tasks percentage is greated than 50%
