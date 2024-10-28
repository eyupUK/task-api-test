@petCRUD
Feature: Pet Store CRUD operations
  As a user
  I want to perform both valid and invalid CRUD operations on pets
  So that I can verify the API responses with the correct body structure

  ### Positive Scenarios ###

  Scenario Outline: Add a pet with valid fields
    Given I have the pet data with name "<petName>" and photoUrls
      | <photoUrl1> |
      | <photoUrl2> |
      | <photoUrl3> |
    When I send a POST request to add the pet
    Then the response status should be 200
    And the pet's name should be "<petName>"
    And the pet's photoUrls should contain
      | <photoUrl1> |
      | <photoUrl2> |
      | <photoUrl3> |
    And response should match the schema

    Examples:
      | petName     | photoUrl1   | photoUrl2   | photoUrl3   |
      | abcnamexyz  | photoUrls51 | photoUrls61 | photoUrls71 |
      | anothername | photoUrls81 | photoUrls91 | photoUrls01 |

  Scenario: Get a valid pet by ID
    Given I have the pet data with name "testingmakesbetterquality" and photoUrls
      | photoUrls51 |
      | photoUrls61 |
      | photoUrls71 |
    When I send a POST request to add the pet
    When I send a GET request to get the pet by ID
    Then the response status should be 200
    And the pet's name should be "testingmakesbetterquality"
    And response should match the schema


  Scenario: Update a valid pet's name
    Given I have the pet data with name "nametobeupdated" and photoUrls
      | photoUrls51 |
      | photoUrls61 |
      | photoUrls71 |
    When I send a POST request to add the pet
    When I send a PUT request to update the pet's name to "fuNNyrabbit"
    Then the response status should be 200
    And the pet's name should be "fuNNyrabbit"


  Scenario: Delete a valid pet and delete the already deleted pet (with non-existing ID)
    Given I have the pet data with name "pettobedeleted" and photoUrls
      | photoUrls51 |
      | photoUrls61 |
      | photoUrls71 |
    When I send a POST request to add the pet
    When I send a DELETE request to delete the pet
    Then the response status should be 200
    And the pet should no longer exist
    When I send a DELETE request to delete the pet
    Then the response status should be 404

  ### Negative Scenarios ###

  Scenario: Add a pet with missing required name field
    Given I have the pet data with name "abc" and photoUrls
      | photoUrls51 |
      | photoUrls61 |
      | photoUrls71 |
    When I send a POST request to add the pet without name
    Then the response status should be 405
    And the error message should contain "Invalid input"

  Scenario: Get a pet with non-existent ID
    Given the pet exists with id "00000000000"
    When I send a GET request to get the pet by ID
    Then the response status should be 404
    And the error message should contain "Pet not found"

  Scenario: Update a pet with invalid body (without photoUrls)
    Given I have the pet data with name "petnottobeupdated" and photoUrls
      | photoUrls |
    When I send a POST request to add the pet
    When I send a PUT request to update the pet's name to "funnybuddy" without photoUrls
    Then the response status should be 405
    And the error message should contain "Validation exception"
    When I send a GET request to get the pet by ID
    Then the pet's name should be "petnottobeupdated"

