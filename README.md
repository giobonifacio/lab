<p align="center">

<h3 align="center">LAB ASSIGNMENT</h3>

  <p align="center">
This assignment creates an API to enable scientits to create its own categories with a set of attribute definitions and then add items to those categories following the attribute definitions of each category.
</p>
  

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#installation">Installation</a>
    </li>
    <li><a href="#considerations">Considerations</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#AdditionalFeatures">Additional Features</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
Scientists need a digital solution to store and identify various items used in their experiments.
These items come in different forms and might include samples, chemicals, devices, etc...!
</br>

### Built With

* Java 15
* Spring Boot 2.4.2
* MongoDB 


### Installation

1. Setup a local MongoDB server
2. Clone the repo
   ```sh
   https://github.com/giobonifacio/lab.git
   ```
3. Set the environment variables for your MongoDB server at application.properties
4. Execute the project and you're good to go!   


<!-- Considerations -->
## Considerations
This project does not provide any UI development. </br></br>
I decided to use a NoSQL database given the flexibility that it provides 
considering that users would dynamically define attributes for categories, and the items would then need to meet those attributes criteria in order to be added to a category.
</br></br>

The basic project structure is as follows
![alt text](src/main/resources/Lab%20Application%20structure.png)
</br></br>

A Trello board was created to help with a to-do list. The trello board can be seen on https://trello.com/invite/b/isCpdIOX/f361ed39f679a5edc226e75d4ad7568c/labforward-lab-items-list </br></br>



<!-- Usage -->
## Usage
A Postman collection was added to the resources folder to help out testing the API. Generic documentation can be found on https://documenter.getpostman.com/view/14507756/TW77f385.



<!-- Additional Features -->
## AdditionalFeatures
There are several features that would improve this solution and that I would work on have I had more time to do so. Those features can be found at the Trello Board and they include:
* Create a docker container for the application embedded with a MongoDB Server.
* Add Security and Authentication layer to the application
* Create Swagger API documentation
* Find Category by name
* Check if category allows undefined attributes on items
* Check if item value type corresponds to attribute definition type when attribute definition demands 'shouldCheckType'
* Move items from categories