# Spring Boot and DynamoDB

A Spring Boot project using DynamoDB.  A goal of mine is to explore and understand DynamoDB Single Table design. 

A good article on the subject is [The What, Why, and When of Single-Table Design with DynamoDB](https://www.alexdebrie.com/posts/dynamodb-single-table/)

## Single Table Design

A few observations working with single-table-design.

### Partition Key and Sort Key

I used the pattern seen in a few articles where my PK is in the form `ACCOUNT#<unique-identifier>` for example
`ACCOUNT#brett`.  My SK is in the same format `PROFILE#

### Access Patterns

The idea of storing any data structure in the table is interesting, but it comes down to how will applications
access that data.  In my case, I'm storing accounts and profiles.  My application primarily looks profiles where profiles
must belong to an account.  For the purposes of my application the single-table is really a account profiles table.

1. 

## Application Concept

The application is a profile manager.  Profiles could be anything but the focus are things like address profiles,
contact profiles, and employment profiles.

As I started to work with Dynamo and idea came to mind about managing profiles and how that could make up an identity.

The idea is one could have many addresses, possibly the history of all the places they have lived.  

Contact profiles could be the different ways a person wants to share their contact details like first name, last name, 
email, and phone number.  Depending on the requesting entity you may want to share a particular contact detail.

The vision is there is a centralized place where a person stores and manages their information.  Something along the 
lines of an identity service.  Could this be something governments provide, could this be something a blockchain/smart 
contracts provide?

Let's say I am applying for a job, the employer requests my contact details and my employment history from the identity 
service. The request is sent to me for authorization where I get to choose the contact details and what employment 
history I want them to use.  

I am applying to rent an apartment and the property manager needs my rentals history.  I would grant them access 
to my current and previous addresses.  I could limit the number of addresses they receive or allow them the all the 
addresses.

The identity service is an interesting idea that needs more thought.  

My goal is to write an frontend in Angular or React that interacts with this service.







