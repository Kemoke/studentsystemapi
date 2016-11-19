# API HOST: https://api.sis.kemoke.net

# API ROUTES:

## Auth routes:
```
POST    /auth/login             form params: email, password            return: json containing jwt token and user type
POST    /auth/register          form params: username, email, password  return: user
```

Za ove dolje rute treba staviti u header 'X-Auth-Token' jwt token iz login rute.
Ako istekne token, nevalja ili ga nema izbacice 401 error sa porukom.

## Student routes
```
GET     /students  params: none  return: student list
POST    /students  params: username,password,email,studentId,firstname,lastname,semester,year,cgpa  return: added student
GET     /students/self  params: none  return: logged in student
GET     /students/:id  params: none  return: student by id
PUT     /students/:id  params: username,password,email,studentId,firstname,lastname,semester,year,cgpa  return: edited student
DELETE  /students/:id  params: none  return: "deleted"
```
## Instructor routes
```
GET     /instructor  params: none  return: Instructor list
POST    /instructor  params: username,email,password,instructorId,firstname,lastname  return: added Instructor
GET     /instructor/self  params: none  return: logged in Instructor
GET     /instructor/:id  params: none  return: Instructor by id
PUT     /instructor/:id  params: username,email,password,instructorId,firstname,lastname  return: edited Instructor
DELETE  /instructor/:id  params: none  return: "deleted"
```
## Department routes
```
GET     /departments  params: none  return: Department list
POST    /departments  params: name  return: added Department
GET     /departments/:id  params: none  return: Department by id
PUT     /departments/:id  params: name  return: edited Department
DELETE  /departments/:id  params: none  return: "deleted"
```
## Program routes
```
GET     /programs  params: none  return: Program list
POST    /programs  params: name,departmentId  return: added Program
GET     /programs/:id  params: none  return: Program by id
PUT     /programs/:id  params: name,departmentId  return: edited Program
DELETE  /programs/:id  params: none  return: "deleted"
```
## Course routes
```
GET     /courses  params: none  return: Course list
POST    /courses  params: programId,name,code,ects  return: added Course
GET     /courses/:id  params: none  return: Course by id
PUT     /courses/:id  params: programId,name,code,ects  return: edited Course
DELETE  /courses/:id  params: none  return: "deleted"
```
## Section routes
```
GET     /sections  params: none  return: Section list
POST    /sections  params: courseId,capacity  return: added Section
GET     /sections/:id  params: none  return: Section by id
PUT     /sections/:id  params: courseId,capacity  return: edited Section
DELETE  /sections/:id  params: none  return: "deleted"
```
#Not implemented
## Student action routes
```
GET     /student/courses  params: none  return: list of available courses including sections
GET     /student/sections  params: courseId  return: list of available sections for course
POST    /student/register  params: sectionId  return: registered section
POST    /student/unregister  params: sectionId  return: unregistered section
```