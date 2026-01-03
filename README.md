# QA_Projects - Automation Testing Guide

This repository contains an API automation test project (Cucumber + Java). This README documents a step-by-step automation testing process for Login and User Validations.

## Table of contents

- Project overview
- Prerequisites
- Project structure
- How tests are organized
- Step-by-step test process
  - Prepare environment
  - Run tests locally
  - Interpret reports
  - Troubleshooting
- Example test scenarios
- Next steps / improvements

## Project overview

The project uses Java with Cucumber for BDD-style API tests. Tests are located under `src/test/java`, and feature files are under `src/test/resources/features`.

Key files:

- `src/test/resources/features/api.feature` - Gherkin scenarios for login and user validation
- `src/test/java/com/runner/TestRunner.java` - JUnit/Cucumber test runner
- `src/test/java/com/runner/TestRunner.java` - JUnit/Cucumber test runner
- `src/test/java/com/stepdefinitions/backend/UserLoginStepDefs.java` - Backend step definitions for login (API)
- `src/test/java/com/stepdefinitions/backend/UserValidationStepDefs.java` - Backend step definitions for user validation (API)
- `src/test/java/com/stepdefinitions/frontend/UIValidationStepDefs.java` - Frontend step definitions for UI validation (Selenium)
- `pom.xml` - Maven project and dependency management
- `target/cucumber-report.html` - Generated test report after running tests

## Prerequisites

- Java JDK 8+ installed and `JAVA_HOME` set
- Maven installed and available in `PATH`
- Internet access to download dependencies (first run)

On Windows PowerShell you can check:

```powershell
java -version
mvn -version
```

## How tests are organized

- Feature files describe behavior in Given/When/Then Gherkin format.
- Step definition classes implement steps and use HTTP client libraries/assertions.
- The TestRunner wires Cucumber to JUnit to execute feature files.
- Test reports are generated to `target/` by the Cucumber/JUnit reporter.

## Step-by-step automation testing process: Login and User Validations

This section provides a reproducible workflow to execute and validate the Login and User Validation automated tests.

### 1) Understand the feature and scenarios

Open the feature file `src/test/resources/features/api.feature`. Read scenarios related to login and user validation. Typical scenarios include:

- Successful login with valid credentials
- Failed login with invalid credentials
- Retrieve user profile after login
- Validate user attributes (name, email, roles)

Map each scenario to its step definition implementation in the `com.stepdefinitions` package.

### 2) Prepare test data and environment

- Confirm API base URL and environment variables (if any). These may be configured in a properties file or environment variables used by step definitions. Search the code for `System.getProperty` or `System.getenv` to find configurable values.
- If tests rely on a running backend, ensure the API server is accessible. For pure API tests, use test/staging endpoints.
- Prepare any test accounts required for login. If account creation is part of tests, ensure cleanup exists.

### 3) Run tests locally (Maven)

From the project root run the Maven test phase. In PowerShell:

```powershell
cd e:/projects/QA_Projects
mvn clean test
```

This will compile code, run tests via the `TestRunner`, and generate reports under `target/`.

If you want to run a specific feature or tags, adjust the Maven command or TestRunner configuration. Example using `-Dcucumber.options`:

```powershell
mvn -Dtest=com.runner.TestRunner -Dcucumber.options="--tags @login" test
```

or run a single JUnit test class using Maven:

```powershell
mvn -Dtest=TestRunner test
```

### 4) Inspect reports and logs

After the run, examine `target/cucumber-report.html` for a human-readable report. For JUnit XML, look under `target/surefire-reports` for XML files and dumpstream logs.

Also review console output for stack traces and HTTP response bodies returned by failed steps.

### 5) Troubleshooting failures

- Verify API endpoint URL and credentials.
- Check for environment-specific differences (ports, auth tokens).
- Re-run failing scenarios locally with more logging enabled in step definitions.
- If intermittent failures happen, check for rate limits or test data collisions.

### 6) Validate test coverage and add assertions

For user validations ensure assertions cover all expected attributes. Example assertions:

- Status code is 200 for successful retrieval
- Response JSON contains `id`, `email`, `name`, and `roles`
- Email format validation, non-empty name

### 7) Continuous integration (optional)

Add a CI pipeline (GitHub Actions, Azure Pipelines, etc.) that runs `mvn clean test` and archives `target/cucumber-report.html` and `target/surefire-reports`.

## Example scenarios and mapping

- Feature: Login
  - Scenario: Successful login -> `UserLoginStepDefs.login_successful()`
  - Scenario: Invalid login -> `UserLoginStepDefs.login_invalid()`

- Feature: User validation
  - Scenario: Get user profile -> `UserValidationStepDefs.get_profile()`
  - Scenario: Validate email and roles -> `UserValidationStepDefs.validate_attributes()`

Note: step definitions are organized into backend (API) and frontend (UI) packages. Example mappings:

- Feature: Login (API)
  - Scenario: Successful login -> `com.stepdefinitions.backend.UserLoginStepDefs`
- Feature: User validation (API)
  - Scenario: Get user profile -> `com.stepdefinitions.backend.UserValidationStepDefs`
- Feature: UI - Login & validation
  - Scenario: UI - Successful login and user validation -> `com.stepdefinitions.frontend.UIValidationStepDefs`

## Commands reference

- Run full test suite:

```powershell
mvn clean test
```

- Run only tests tagged `@login`:

```powershell
mvn -Dcucumber.options="--tags @login" test
```

- Generate tests and open report (after running tests):

```powershell
Start-Process target\cucumber-report.html
```

## Notes and next steps

- If your tests need encrypted secrets, store them securely (GitHub Secrets, Azure Key Vault) and make them available as environment variables in CI.
- Consider adding a dedicated `config` or `test-resources` folder for test data and environment mappings.
- Add a GitHub Actions workflow to run tests automatically on PRs and push to main.

## Publishing this project to GitHub

You can publish this repository as a public project so SDETs and developers can reference it.

1. Initialize a local git repository (if not already):

```powershell
cd e:/projects/QA_Projects
git init
git add .
git commit -m "Initial commit - automation project"
```

2. Create a new repository on GitHub (choose Public). If you prefer the CLI approach:

```powershell
gh repo create <your-username>/QA_Projects --public --source=. --remote=origin
```

3. Push to GitHub:

```powershell
git push -u origin main
```

4. Enable branch protection and add required reviewers if desired. In repository Settings -> Branches -> Add rule for `main`.

5. Add any secrets needed for CI under Settings -> Secrets -> Actions (for example API keys or test credentials). Use these secrets in your workflow using `secrets.MY_SECRET`.

6. The included GitHub Actions workflow (`.github/workflows/ci.yml`) will run `mvn test` on push and PRs and archive the `target/cucumber-report.html` as an artifact.

Contributing

- Add a short `CONTRIBUTING.md` if you'd like to outline contribution guidelines (coding style, PR process, testing expectations).
- Use issues and pull requests for changes. Label issues like `enhancement`, `bug`, or `question` to help contributors.
