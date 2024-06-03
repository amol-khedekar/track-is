# About 
Track-is , an simple issue tracker system is full-stack web application allowing users to effieciently manage bugs and stories. System incorporates multiple types of users. Hence security becomes utmost important and hence system is implemented using Role Based Access Control (RBAC). Developed robust REST APIs in Spring Boot with JWT Authentication. A responsive frontend calls these APIs made in React frontend. 

# Technology Stack -
* **Languages**: Java, Typescript
* **Frameworks:** Spring Boot, React
* **Databases:** MySQL DB, Redis
* **Build tools:** Maven, Vite
* **Servers:** Tomcat Server


# REST API includes-
* CRUD APIs
* Role Based Access Control mechanism for resources
* Authentication & authorization (JWT Auth)



# List of REST APIs can be called 

## Auth API
| HTTP Method | URI | Description                                                          | 
| ----------- | --- |----------------------------------------------------------------------|
| POST        | /api/auth/login | Sign in and get access token + refresh token pair                    |
| POST        | /api/auth/token | For Expired Token, Create and get an new access + refresh token pair |

## User API
| HTTP Method | URI | Description | 
| ----------- | --- | ----------- |
| GET         | /api/users | Get all users |
| POST        | /api/users | Create/register a user |
| GET         | /api/users/{username} | Get user data | 
| PATCH       | /api/users/{username} | Update user data | 
| DELETE      | /api/users/{username} | Delete user | 

## Project API
| HTTP Method | URI | Description | 
| ----------- | --- | ----------- |
| GET         | /api/projects | Get all projects |
| GET         | /api/projects?user={username} | Get all projects where user is the owner |
| GET         | /api/projects?collaborator={username} | Get all projects where user is a collaborator |
| POST        | /api/projects | Create a project |
| GET         | /api/projects/{projectId} | Get project data | 
| PATCH       | /api/projects/{projectId} | Update project data | 
| DELETE      | /api/projects/{projectId} | Delete project | 
| DELETE      | /api/projects/{projectId}/issues | Get project issues | 
| GET         | /api/projects/{projectId}/collaborators | Get project collaborators | 
| PUT         | /api/projects/{projectId}/collaborators/{username} | Add a project collaborator | 
| DELETE      | /api/projects/{projectId}/collaborators/{username} | Delete project collaborator | 

## Issue API
| HTTP Method | URI | Description | 
| ----------- | --- | ----------- |
| GET         | /api/issues | Get all issues |
| POST        | /api/issues | Create an issue |
| GET         | /api/issues/{issueId} | Get issue data | 
| PATCH       | /api/issues/{issueId} | Update issue | 
| DELETE      | /api/issues/{issueId} | Delete issue | 
| GET         | /api/issues/{issueId}/comments | Get issue comments | 

## Comment API
| HTTP Method | URI | Description | 
| ----------- | --- | ----------- |
| GET         | /api/comments | Get all comments |
| POST        | /api/comments | Create a comment |
| GET         | /api/comments/{commentId} | Get comment data | 
| PATCH       | /api/comments/{commentId} | Update comment | 
| DELETE      | /api/comments/{commentId} | Delete comment |  



## Sample Requests and Responses:
A GET request to ```http://localhost:8080/api/projects/2``` returns the project with an id of 2:

#### Response:

```json
{
    "id": 2,
    "title": "Expense Management System",
    "description": "An application to track and manage personal expenses.",
    "private": true,
    "owner": {
        "id": 2,
        "username": "johndoe",
        "email": "johndoe@trackis.com",
        "roles": [
            "ADMIN"
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/projects/2"
        },
        "owner": {
            "href": "http://localhost:8080/api/users/johndoe"
        },
        "collaborators": {
            "href": "http://localhost:8080/api/projects/2/collaborators"
        },
        "issues": {
            "href": "http://localhost:8080/api/projects/2/issues"
        }
    }
}

```

A GET request to ```http://localhost:8080/api/projects/4``` returns the project with an id of 4:

#### Response:

```json
{
    "id": 4,
    "title": "Online Learning Portal",
    "description": "A platform for online courses and certifications.",
    "private": true,
    "owner": {
        "id": 4,
        "username": "mikejohnson",
        "email": "mikejohnson@trackis.com",
        "roles": [
            "USER",
            "INSTRUCTOR"
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/api/projects/4"
        },
        "owner": {
            "href": "http://localhost:8080/api/users/mikejohnson"
        },
        "collaborators": {
            "href": "http://localhost:8080/api/projects/4/collaborators"
        },
        "issues": {
            "href": "http://localhost:8080/api/projects/4/issues"
        }
    }
}


```
