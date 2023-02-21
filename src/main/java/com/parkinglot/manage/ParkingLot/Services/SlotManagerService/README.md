### Vulnerabilities

1. Consider the flow, Slot is booked on cache, and status is changed in database, what happens if response does not reach user.
due to server down/ network problem.

2. One way would be to have an acknowledgement column in slot database. And after an event/time reset status of filled and unacknowledged slots to unfilled. If there is server reset ofcourse we will reset status, but at what time.

3. Maybe we could solve it using acknowledgement begin and end time. After 10 mins of no acknowledgement, purge the entries.
