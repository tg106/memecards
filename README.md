# MemeCards
MemeCards is a fun, interactive yet simple turn-based card game that everyone can enjoy.

## Getting Started
1. Clone the repo.
- `git clone https://code.cs.umanitoba.ca/comp3350-summer2019/cards-9.git`
2. Import into Android Studio.
- In Android Studio, start a new project by importing the cloned repo.
3. Build the project.
- Click on the green hammer icon at the top of the page in Android Studio to build the project
4. Run the app.
- Click the green play button at the top of the page in Android Studio to install and start the app on the android device.

## Testing
There are 2 types of tests and test suites exists for both:
- Local Unit Tests
    - These tests runs on your local development environment. They are mainly used to the the login of your code.
    - Located in `app/src/test/java/com/example/memecards/`. Run the `TestSuite` class in the directory to run all tests.
- Instrumented Tests
    - These tests require an android enrivonment to run. They are used to tests android dependent code.
    - Located in `app/src/androidTest/java/com/example/memecards/`. Run the `InstrumentedTestSuite` class in the directory to run all tests.

## Related Documents
- [Vision](VISION.md)
- [Architecture](ARCHITECTURE.md)
- [Retrospective](RETROSPECTIVE.md)
