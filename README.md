# retry-until

This code demonstrates how to honor all user and system needs in a retry and waiting system in a service.

Honors:
* The users's need to be notified of progress and delay.
* The user's need to cancel a request.
* To stop beofre the time the user will lose patience with a process as understood by your product owner.
* The downstream system's requests to delay execution.
* The downstream system's need for exponential delay.
* The system's need to not have retries happen all at once
* They system's need to not retry when the client provided a bad request.

You can find the core code in [RetryExecutor.java](https://github.com/AveryRegier/retry-until/blob/master/src/main/java/regier/retry/RetryExecutor.java).

This was the example code from [To Retry, or Not to Retry](https://github.com/AveryRegier/presentations/blob/master/To%20Retry%20or%20Not%20to%20Retry.pdf) given at Prairie Code 2019.
