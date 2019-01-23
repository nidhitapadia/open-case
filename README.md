Travel API Client 
=================

Clone this repo and start it (on windows systems use the gradlew.bat file):

`./gradlew bootRun`

to list all tasks:

`./gradlew tasks`

To view the assignment (after starting the application) go to:

[http://localhost:9000/travel/index.html](http://localhost:9000/travel/index.html)

Overview of the changes done 
============================
- Implemented api to get the airport list (api/v1/airports) which gives pagenated results so that the results can be displayed in pagenated format on frontend
- Implemented api to get airport details (api/v1/airports/{code})
- Implemented api to get fare details (api/v1/fares/{origin}/{destination}) which provides the fare details with the airport details (retreived asynchronously)
- Enabled the actuator endpoints for metrics and prometheus (/actuator/prometheus and /actuator/metrics) to get the traffic metrics data. The data form tag http_server_requests can be used to get the details requested in the task.
- Updated the thread name for async tasks to use async-task-{thread_id}
- Added basic exception handling
- Used CompletableFuture for calling services asynchronously
- Used hateos api to travers the response form the mock service. If we need to add more feilds in the api response which are provided by mock service the data can be mapped automatically by hateos api.

Possible changes
============================
- Improve API Exception handling with proper error codes and messages
- More input validation can be added.
- Unique id in the request using UUID.