Feature: Form functionality

Background:
  Given user on Home page 

@Form  
Scenario Outline: ab testing is done 
  When user on form list page 
  When user create a new form 
  
 
 
@appointment 
Scenario Outline: appointment page 
  When user drag and drop
  
  
@Redline_realtime   
Scenario Outline: appointment page 
  When redline on current time 
