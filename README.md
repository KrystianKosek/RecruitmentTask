# OpenX_Intern_Task

The task was about downloading users and posts in a json format from an endpoint and then parsing the data and connecting posts with users.
With the given data I had to return how many posts each user wrote and check the posts for duplicates.
Finally, I found the nearest neighbor for each user. <br/>
My solution for this task uses OkHttp to make a connection and Retrofit REST Client to retrieve JSON from webservice. I used Lombok library to automatically
generate getters and setters. Tests are written in JUnit 5 ussing AssertJ library which increases readability of the code and Mockito with BDDMockito to allow
mocking objects.
