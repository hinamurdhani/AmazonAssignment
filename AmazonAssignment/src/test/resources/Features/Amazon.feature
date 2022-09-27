Feature: Amazon Automation
	
	
Scenario: validate use login using valid creds

	Given browser is open
	And user move to the login url
	When user check login page is loaded properly or not
	When user enter username and password
	|username|password|
	|||
	Then user clicks on login button
	Then user should redirect to home page
	Then user search for below item
	|itemname|mobile phone|
	Then user click on the product
	Then user add the given number of quanity
	|quantity|2|
	Then user click on add to cart button
	Then user logged out from the system
	Then user close the driver
	

	
	
