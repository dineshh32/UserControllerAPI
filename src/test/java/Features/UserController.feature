Feature: UserController feature
  Background: Creating a user
    Given user details


    Scenario: verify if a user resource can be added
      When creating a user
      Then user is created

    Scenario: verify if a user can be updated
      When updating a user
      Then user is updated

      Scenario: verify if an error is thrown when name is not specified while creating
        When creating a resource without name
        Then error message is displayed

        Scenario: verify if an error is thrown when address is not specified while creating
          When creating a resource without address
          Then error message for address is displayed

          Scenario: verify if an error is thrown when marks is not specified while creating
            When creating a resource without marks
            Then error message for marks is displayed

             Scenario: verify if user is able to access all resources
               When accessing the resouces under user
               Then resources are displayed

               Scenario: verify if user is able to access a specific resource
                 When accessing a specific resource under user
                 Then specific resource is displayed

            Scenario: verify if a resource can be deleted
              When deleting a resource
              Then resource should be deleted

              Scenario: verify if an error is thrown when name is not specified while updating
                When updating a user without specifying a name
                Then error message asking for name is displayed

                Scenario: verify if an error is thrown when address is not specified while updating
                  When updating a user without specifying an address
                  Then error message asking for address is displayed

                  Scenario: verify if a resource cannot be deleted when id is not specified
                    When deleting a resource without specifying id
                    Then error is thrown

                    Scenario: verify if multiple resources can be created
                      When creating multiple users
                      Then multiple users are created








