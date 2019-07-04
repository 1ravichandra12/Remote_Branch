Feature: To search cucumber in google

  @Regression
  Scenario: Cucumber Google
    Given I am on "google" search page
    When I type "cucumber"
    Then I click search button
    Then I clear search textbox

  @Login
  Scenario Outline: Login to internal user
    Given I am in ford internal login page    
     And click on the login link     
     When I enter Username as "<username>" and Password as "<password>"
  	 Examples:    
    |username|password|
    |rchand78 |Ford@123|
    
  