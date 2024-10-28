Petstore API Test Automation Framework
This framework is designed to test the CRUD operations of the Petstore Sample API using Java, Maven, and several libraries for efficient, scalable, and easily understandable automated testing. The framework employs Cucumber BDD for structured and readable test cases, Rest Assured for API interactions, and Allure and Cucumber HTML for comprehensive reporting.

Table of Contents
Technologies Used
Project Structure
Setup and Installation
Running Tests
Generating Reports
Configuration
Sample Test Scenarios
Technologies Used
Java - Language used for implementing test cases
Maven - Dependency management and build tool
Cucumber BDD - Behavior-Driven Development to write clear and understandable tests
Rest Assured - For making HTTP requests to the API
Cucumber HTML Report - For BDD-style reporting
Allure Report - For detailed, visual test execution reports
JsonSchemaValidator - For validating JSON response schemas
Page Object Model (POM) - For modularizing and organizing code

Project Structure
📦 src
├── 📂 main
├── 📂 test
│   ├── 📂 java
│   │   ├── 📂 com.eyup            # Configuration classes for setting up the test environment
│   │   │   ├── 📂 pages               # API client class to set Request Methods
│   │   │   ├── 📂 runners                # Test runners for executing scenarios
│   │   │   ├── 📂 stepDefinitions             # Test runners for executing scenarios
│   │   │   │   ├── 📂 api     # Step definition classes for API steps
│   │   │   ├── 📂 utils     # SUtilities and helpers
│   ├── 📂 resources
│   │   ├── 📂 features            # Cucumber feature files (BDD scenarios)
│   │   │   └── 📜 PetCRUD.feature # Scenario for CRUD operations
│   │   └── 📂 data             # JSON schemas for response validation
└── 📄 README.md

```bash
git clone https://github.com/your-username/petstore-api-framework.git
cd petstore-api-framework
```
