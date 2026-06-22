## Summary

- Added `GET /api/profiles/{username}/stats` for user activity counts.
- Added integration coverage for successful stats retrieval plus `404` and `422` error cases.
- Updated GitHub Actions to build and test on JDK 17 and 21 with JUnit report publishing.

## Copilot Agent Notes

Prompt used:

> Add integration tests for the new `GET /api/profiles/{username}/stats` endpoint. Cover the happy path by registering a user, seeding article/comment/favorite activity, and asserting the returned counts. Also cover an unknown username returning `404` and a blank username returning `422`. Follow the existing JUnit + Unirest controller test style and use `AppRule`.

What worked well:

- The agent-oriented prompt was specific about endpoint behavior, test style, and expected status codes.
- The existing `AppRule` made full-app integration tests straightforward.

Manual fixes/refinements:

- Seeded activity directly through Exposed because article/comment controllers in this repo are currently stubbed.
- Added a readiness check to `AppRule` so tests wait for Ktor to accept connections instead of relying on a fixed sleep.
- Kept the endpoint public because profile reads are optional-auth in the existing routing.

Customer workflow improvement:

- I would provide the agent with a short architecture note listing which controllers are stubbed versus fully implemented, so it does not generate tests that depend on incomplete article/comment APIs.

## Validation

- [x] `./gradlew test`
- [x] `./gradlew build`

## Tradeoffs

- The RealWorld Postman spec workflow was not added as a required CI check because the current repository has unrelated stubbed endpoints that would fail the full spec suite.
