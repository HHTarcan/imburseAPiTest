
  Feature: Create an Order Functionality
   In order to payout money to or collect money from your customers,
   As a customer, you need to generate Orders and Instructions.

Background:
  Given authorized with access token from the endpoint "/order-management"

# functions of create an order
  Scenario:Order creation
    When send the post request to end point "/v1/order-management" with orderRef "Htarcan"
    Then status code should be 201

  #Order reference availability, Mandatory
    # "GET Order" endpoint is not working properly-- should be 200 but was 400 and "GET" "errorCode":"ORDER_NOT_FOUND"-400 so

    Scenario: Order Reference availability for order creation
      And send the get order request to end point
      Then status code should be 200

# Negative tests orderRef

    # order can be created with empty orderRef
    Scenario: Order creation with empty order reference
      When send the post request to end point with send the orderRef as "     "
      Then status code should be 400

       # order can be created with invalid chars orderRef
    Scenario: Order creation with invalid order reference with invalid chars
      Then send the order reference as b "?asdasd/*/* 1231231231"
      Then status code should be 400

    #order can be created with too long
    Scenario: Order creation with too long order reference
      When send the order reference as a "_asdasd---_.asdasdasrtytryrtrtytriuhihidhgdsfghshkjdsfhgks"
      Then status code should be 400

    Scenario: Order reference should be unique
      When create another order with same orderRef "Htarcan"
      Then status code should be 400

  #METADATA negative

    #  put request should be whole request body in documentation
    Scenario: Create an order with too long metadata key
      And first metadata key "key1meta" replace with value "sdfsfsfioerfiejfoijeiejrfjerofjeoifjeojfeorfjerionjdvmnERTERTERTSF"
      Then status code should be 400

      # value is accept long one
    Scenario: Create an order with too long metadata value
      And first metadata value "jkl" replace with value "sdfsfsfioerfiejfoijeiejrfjerofjeoifjeojfeorfjerionjdvmnERTERTERTSF"
      Then status code should be 400

    Scenario: Order creation with same metadata key values
      When send the post request to end point "/v1/order-management" with orderRef "Htarcan"
      And first metadata key "key1meta" and second metadata key replace with value "key1meta"
      Then status code should be 400

     # When first metadata value replace with value null
    Scenario: Order creation with null value for metadata
      And first metadata value "jkl" replace with value null
      Then status code should be 400





