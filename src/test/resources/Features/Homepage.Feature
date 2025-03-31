Feature: Form functionality

Background:
  Given user on Home page 

@Form  
Scenario Outline: ab testing is done 
  When user on form list page 
  When user create a new form 
  
@Delete_Form  
Scenario Outline: ab testing is done 
  When user on form list page 
  Then validate delete form
  
 
 
 
@appointment 
Scenario Outline: appointment page 
  When user drag and drop
  
  
@Redline_realtime   
Scenario Outline: appointment page 
  When redline on current time 

  
 @date
Scenario Outline: appointments as per date  
  When Verify appointments as per date  
 
  
 @add_client 
Scenario Outline: add client functionlaity  
  When add <first_name> and <Last_Name> and <cCode> and <Mnumber> and <Email> and <Gender> and <DOB> and <HAddress>
  Then verify create a new client 
   
   Examples:
   | first_name |  Last_Name  |  cCode  | Mnumber		      | 		Email			   |   Gender  |   DOB  				 | 		HAddress      |
   |  "QA"      |  "Test"		  |   "+91"	| 	"9090909090"	| "test@gmail.com" | 		"male" |   "01-01-2025"  | "	Test Address" |
   
   
