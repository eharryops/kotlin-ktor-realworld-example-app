# Copilot Agent Issue: User Activity Stats Tests

## Goal

@copilot Please add integration tests for the new `GET /api/profiles/{username}/stats` endpoint.

## Context

The endpoint returns:

```json
{
  "stats": {
    "articlesCount": 5,
    "commentsCount": 12,
    "favoritesCount": 3
  }
}
```

The project uses JUnit and Unirest integration tests under `src/test/kotlin/io/realworld/app/web/controllers/`.
Use `AppRule` to boot the Ktor app, and follow the existing controller test style.

## Test Coverage Requested

- Happy path: register a user, seed article/comment/favorite activity in H2, call `/api/profiles/{username}/stats`, and assert the returned counts.
- Error case: unknown username returns `404`.
- Error case: blank username returns `422`.

## Notes

- Keep the stats endpoint public, matching the existing optional-auth profile read route.
- Prefer focused assertions on HTTP status and the `stats` response body.
- Avoid broad refactors outside the stats endpoint and tests.
