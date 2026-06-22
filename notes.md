# Interview Project Notes

## Summary

For this exercise, I added a new user activity stats API to the Kotlin/Ktor RealWorld project.

The feature gives a quick summary of a user's activity, including how many articles they have written, how many comments they have made, and how many articles they have favorited.

The new endpoint is:

```text
GET /api/profiles/{username}/stats
```

I also added automated tests, GitHub Actions CI, and documentation for how I tested the feature. The exact testing and submission commands are in `steps.md`.

## What I Focused On

I tried to keep the work focused on the interview requirements:

- Add one meaningful API feature.
- Follow the existing controller, service, and repository style.
- Add tests that prove the feature works.
- Include error handling for bad or missing input.
- Add CI so the project is checked automatically.
- Document the Copilot/agent workflow clearly.

## Testing Approach

I added integration tests for the stats endpoint instead of only testing isolated functions. That felt more useful because it proves the route, controller, service, repository, and database query work together.

The tests cover:

- A successful stats request.
- A missing user.
- A blank username.

I also manually tested the endpoint by starting the API, creating a user, and calling the stats endpoint. A screenshot of that manual test is included in the repo and referenced from `steps.md`.

## Issues I Ran Into

The main challenge was that the original project was old and not fully complete.

Some build dependencies were outdated, so the project did not work cleanly with the Java versions required by the exercise. I updated the build setup enough to make the project build locally and in CI.

Some parts of the RealWorld app were also stubbed, especially around articles and comments. Because of that, the stats tests seed the test database directly instead of relying on unfinished article/comment APIs.

The test server startup was also a little unreliable at first. The tests sometimes tried to call the API before the local server was ready. I fixed that by making the test setup wait until the server is actually accepting connections.

## Copilot/Agent Work

I prepared GitHub issue #1 with a Copilot prompt to generate and refine tests for the new endpoint. Copilot assignment was not available in this repository, so I reviewed and refined the tests manually.

I also added a PR template that records the prompt, what worked well, what needed cleanup, and how I would improve the workflow for a customer.

The actual command steps for creating the Copilot issue are in `steps.md`.

## Example Prompts That Worked

These are examples of prompts I used during the project that led to useful outcomes:

```text
For part 2 I want to add "option b" api.
```

This helped narrow the work to the user activity stats endpoint instead of trying to implement multiple features.

```text
I don't want to make any major changes that are not needed to the code, only things to make the code work.
```

This helped keep the implementation focused and avoid broad refactors outside the interview requirements.

```text
I want steps located in the repo so users know how I tested it.
```

This led to adding `steps.md`, which documents how to run the tests, start the API, manually test the endpoint, create the Copilot issue, and open the pull request.



```text
Update my notes.md. I don't want to be redundant with the steps.md.
```

This helped separate the project summary from the detailed command steps, making the documentation clearer for different audiences.

## Tradeoffs

I avoided changing unrelated application behavior.

One tradeoff is that I had to update some build-related versions to get the project working with modern Java and GitHub Actions. The Gradle wrapper was updated so CI can run on JDK 21, while the Kotlin compiler still targets JVM 16 to match the project guidance. In a regulated environment, I would treat that differently: document the reason, assess the risk, and get approval before changing build tools or dependency versions.


## Current Status

The feature is implemented, tested, documented, and submitted in pull request #2.

The GitHub Actions build passed after updating the Gradle wrapper so the project could run on both required Java versions.
