Feature: Login functionality

Background:
  Given user on login page

@validlogin
Scenario Outline: User should be able to login with valid data
  When user provide <username> and <password>
  
   