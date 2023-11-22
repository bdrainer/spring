# Spring Boot and DynamoDB

A Spring Boot project using DynamoDB.  A goal of mine is to explore and understand DynamoDB Single Table design. 

A good article on the subject is [The What, Why, and When of Single-Table Design with DynamoDB](https://www.alexdebrie.com/posts/dynamodb-single-table/)

## Single Table Design

Below are some thoughts on single-table-design.

### Partition Key and Sort Key

I used the pattern seen in a few articles.

The Account object has:  

* PK (partion key) and SK (sort key) in the form `ACCOUNT#<unique-identifier>`, for example
`ACCOUNT#johndoe`.

The Profile object has:

* PK in the form `PROFILE#<unique-identifier>`, the unique-identifier is the same value as the one set for the associated account.
* SK in the form `PROFILE#<TYPE>#<userkey>` where TYPE is the profile type and userkey ia a unique value the user sets.

I realize now the Profile PK might be better if it was the same PK as Account, i.e. `ACCOUNT#<unique-identifier>`

**TODO**: Change the PK on Profile to match Account.  The single table is an account table holding data related to the account.
Profiles are related to the account and profiles come in different types.  The `userkey` is a way for the user to distinguis
and have more the one profile of the same type.  One example is address where a user could have a home address, a billing
address, a postal box address, etc.

### Access Patterns

The idea of storing any data structure in the table is interesting, but it comes down to how will the application
access that data.  In my case, I'm storing accounts and profiles.  The app looks up profiles where profiles
must belong to an account.  The single-table is really an account profiles table but could be extended.  The account 
could have a new type for example Pet.  The PK is the same as for Account but the SK is `PET#<TYPE>#userkey`.

**Queries**
1. Get account by username.
2. Get all profiles associated with an account.
3. Get all profiles of a specific type for an account
4. Get a profile of a specific type and specific userkey for an account.


## Application Concept

The application is a profile manager.  Profiles could be anything but the focus are things like address profiles,
contact profiles, and employment profiles.

As I started to work with Dynamo an idea came to mind about managing profiles and how that could make up an identity.

The idea is one could have many addresses, possibly the history of all the places they have lived.  

Contact profiles could be the different ways a person wants to share their contact details like first name, last name, 
email, and phone number.  Depending on the requesting entity you may want to share a particular contact detail.

The vision is there is a centralized place where a person stores and manages their information.  Something along the 
lines of an identity service.  This is similar to sites that allow you to use credentials from Google, Apple, etc. to 
log in.

**Use Case**
I am applying for a job, the employer requests my contact details and my employment history from the identity 
service. The request is sent to me for authorization where I get to choose the contact details and what employment 
history I want them to use.  

**Use Case**
I am applying to rent an apartment and the property manager needs my rentals history.  I would grant them access 
to my current and previous addresses.  I could limit the number of addresses they receive or allow them the all the 
addresses.








