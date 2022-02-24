
Feature: Create an Instruction Functionality
  In order to payout money to or collect money from your customers,
  As a customer, you need to generate Instructions.

  Background:
    Given authorized with access token from the endpoint "/order-management"

# functions of create an instruction

    # create an instruction
   # error; response status 201 should be 202
  Scenario: Successful Created instruction with orderRef
    When send the post request to end point "/v1/order-management" with instructionRef "HHtarcan" and customerRef "HHtarcan1"
    Then status code should be 202

     #Instruction reference availability, Mandatory
  #getInstruyction is 400-- not working
  Scenario: Instruction Reference availability for instruction creation
    And send the get instruction request to end point
    Then status code should be 200

 # Negative tests for instruction reference details
  # testlerden 400 aldik
  Scenario: Instruction creation with empty instructionRef
    #When send the post request to end point "/v1/order-management" with instructionRef "HHtarcan" and customerRef "HHtarcanA"
    When send the post request empty instructionRef
    Then status code should be 400

  Scenario: Instruction creation with invalid chars instructionRef
    When send the post request to end point "/v1/order-management" with instructionRef "HHtarcanA" and customerRef "HHtarcan"
    When send the post request with invalid chars instructionRef "?asdasd/*/* 1231231231"
    Then status code should be 400

  Scenario: Instruction creation with too long instructionRef
    #When send the post request to end point "/v1/order-management" with instructionRef "HHtarcanA" and customerRef "HHtarcan"
    When send the post request with too long instructionRef "_asdasd---_.asdasdasrtytryrtrtytriuhihidhgdsfghshkjdsfhgks"
    Then status code should be 400

######customer reference details

   # Negative tests for customer reference details
  #### iyi
  Scenario: Instruction creation with emtpy customerRef
    And send the post request with empty customerRef
    Then status code should be 400

  Scenario: Instruction creation with invalid chars customerRef
    And send the post request with invalid chars customerRef "?asdasd/*/* 1231231231"
    Then status code should be 400

  Scenario: Instruction creation with too long customerRef

    And send the post request with too long customerRef "_asdasd---_.asdasdasrtytryrtrtytriuhihidhgdsfghshkjdsfhgks"
     Then status code should be 400

#amount of money-negatives

  Scenario: Instruction creation with empty amount
# akbul etmiyor
    When succesfully create an order without instructions
    And add the instruction to this order with  empty amount of money "    "
    Then status code should be 400

  #  3 digiti kabul ediyor
  Scenario: Instruction creation with more than 3 digit amount
    When succesfully create an order without instructions
    And add the instruction to this order with more than 3 digit amount of money "210.012"
    Then status code should be 400

  Scenario: Instruction creation with zero amount
    #kabul etmiyor
    When succesfully create an order without instructions
    And add the instruction to this order with zero amount of money "0.00"
    Then status code should be 400

  #currercy
  # status 201- 202 olmali
  # 400 aldik
  Scenario: valid currency verificaton
    When succesfully create an order without instructions
    And add the instruction to this order with currency number "978"
    Then status code should be 202

    #negative currency
  # kabul etmedi- pozitive
  Scenario: invalid currency
    When succesfully create an order without instructions
    And add the instruction to this order with currency "EURO"
    Then status code should be 400

    #country name
  Scenario: invalid country name verificaton
    When succesfully create an order without instructions
    And add the instruction to this order with country name "IEQ"
    Then status code should be 400

#direction part
  #negative with credit
  #tamam
  Scenario: direction verificaton
    When succesfully create an order without instructions
    And add the instruction to this order with direction "CREDIT"
    Then status code should be 400


