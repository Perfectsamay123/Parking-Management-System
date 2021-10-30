# Parking-Management-System
This is a Spring boot application to register ,park, calculate price and maintain record of vehicle. 

***
## ABOUT THE PROJECT
* Parking Management System is used to manage parking system.
* This service registers a new parking Area with all its capacities and price slab.
* This service also provides parking service to the vechicles and calculates the amount need to be paid for the parked vechicle.
* In addition to that,this service also provides the entire parking history of a vechicle.

## REQUIREMENTS
* Java 1.8
* Maven 3

## SETUP
* Clone the following Repo : [ click_this](https://github.com/Perfectsamay123/Parking-Management-System.git)
* Resolve the dependencies
* Run the application through main driver class and use the below given endpoints.

## REST API
* The following Endpoints are used in the Parking Management System.The Request & Response Body are described as below:

### 1. **/newRegistration**
* Path : /newRegistration
#### Request
![newRegistration](https://user-images.githubusercontent.com/42905279/139541499-5188ff81-646e-49dc-a4b0-f48eb893580f.JPG)
* **Curl Request**:   curl --location --request POST 'http://localhost:8080/newRegistration' \
--header 'Content-Type: application/json' \
--data-raw '{
    "parkingName":"Devi",
    "twoWheelerCapacity":10,
    "fourWheelerCapacity":10,
    "slotPrice":{
        "0-2":10,
        "2-6":20,
        "6-18":30,
        "18-24":40,
        ">24":50
    }
}'
#### Response
![newRegistrationResponse](https://user-images.githubusercontent.com/42905279/139541603-b43bdb50-b481-428e-8a61-7516c78f3506.JPG)

### 2. **/parkVechicle**
* Path : /parkVechicle
#### Request
 ![parkVechicleRequest](https://user-images.githubusercontent.com/42905279/139541993-ce7c6a62-90e7-4877-9780-a2e22f058e49.JPG)
* **Curl Request**:   curl --location --request POST 'http://localhost:8080/parkVechicle' \
--header 'Content-Type: application/json' \
--data-raw '{
   "vechicle":{
       "vechicleNumber":"UP-65CY5458",
       "vechicleType":"Two"
   },
   "parkingName":"Devi"
}'
#### Response
![parkVechicleResponse](https://user-images.githubusercontent.com/42905279/139542021-b0eab2c0-fd65-4acc-85d2-b6086785e222.JPG)

### 3. **/calculateAmount**
* Path : /calculateAmount
#### Request
![CalculateAmountRequest](https://user-images.githubusercontent.com/42905279/139542098-8db205b6-cb88-4d9b-9f56-a7a4ffadb8e5.JPG)

* **Curl Request**:  curl --location --request POST 'http://localhost:8080/calculateAmount' \
--header 'Content-Type: application/json' \
--data-raw '{
   "vechicle":{
       "vechicleNumber":"UP-65CY5458",
       "vechicleType":"Two"
   },
   "parkingName":"Devi"
}'
#### Response
![CalculateAmountResponse](https://user-images.githubusercontent.com/42905279/139542115-3155e670-0167-4464-84b4-63f86fc61461.JPG)

### 4. **/maintainsHistory**
* Path : /maintainsHistory
#### Request
![MaintainHistoryRequest](https://user-images.githubusercontent.com/42905279/139542168-af733008-ea79-4386-9a87-216fb314e6a7.JPG)

* **Curl Request**:  curl --location --request POST 'http://localhost:8080/maintainsHistory' \
--header 'Content-Type: application/json' \
--data-raw '{
    "vechicleNumber":"UP-65CY5458"
}'
#### Response
![MaintainHistoryResponse](https://user-images.githubusercontent.com/42905279/139542180-ff7ceb0e-aa79-43f2-9dac-0c71bd6f6b32.JPG)


***
