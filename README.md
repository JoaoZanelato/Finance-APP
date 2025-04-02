# UFinance

## Overview

**UFinance** is a simple yet efficient finance management application designed to help users track their income and expenses. This application uses **CRUD** (Create, Read, Update, Delete) as its core architecture, providing an intuitive and easy-to-use interface for managing personal finances. The app also supports **user authentication**, including secure login and registration with cryptography, ensuring that each user's data is protected and unique.

## Features

-   **User Authentication**:
    -   Secure **user registration** and **login** with encrypted passwords.
    -   Each user’s data is **isolated** and unique, providing privacy and security.
-   **Financial Data Management**:
    -   Users can add **receipts** and **expenses**, with real-time updates to their financial data.
    -   The application automatically calculates a dynamic **balance** whenever new entries are made.
-   **Data Visualization**:
    -   All financial data is displayed in a clear **table** format.
    -   **Filters** are available to sort and view specific entries based on various criteria (such as date or amount).
-   **Database**:
    -   The application uses **SQLite** to store and manage user data.
-   **Security**:
    -   Passwords are encrypted using Java's **cryptographic libraries** to ensure user data is stored securely.
    -   User-specific data ensures no sharing between accounts.

## Installation

### Prerequisites

-   **Java 8 or higher**: Ensure you have a JDK installed on your machine.
-   **SQLite**: For storing user data.

### Setup

1.  **Clone the repository**:

    ```
    git clone [https://github.com/JoaoZanelato/Finance-APP.git](https://github.com/JoaoZanelato/Finance-APP.git)
    ```

2.  Navigate to the project directory:

    ```
    cd Finance-APP
    ```

3.  Build the project:

    If you're using Maven, you can compile the project with:

    ```
    mvn clean install
    ```

    Alternatively, if you're using Gradle, run:

    ```
    gradle build
    ```

4.  Run the application:

    After building the project, you can run the application by executing:

    ```
    java -jar target/finance-app.jar
    ```

## Usage

### User Registration and Login

Upon starting the application, you'll be prompted to register or login. Provide a username and password. The password will be encrypted before storing it in the database. Once logged in, you can start managing your finances.

### Add Receipts and Expenses

After logging in, navigate to the section where you can add receipts and expenses. Enter the details of the transaction, such as the amount and category. The balance will update automatically with each new entry.

### Filters and Data Display

Your financial entries will be shown in a table format. You can use available filters to sort data by date, amount, or category. You can also update or delete entries as needed.

## Technologies Used

-   Java 8 (or higher): Main programming language for the application.
-   SQLite: Database for storing user data.
-   Java Cryptography API: Secure password encryption for authentication.

## Contributing

We encourage open-source contributions to improve the project! If you would like to contribute, follow these steps:

1.  Fork the repository.
2.  Create a new branch (`git checkout -b feature/your-feature-name`).
3.  Commit your changes (`git commit -m 'Add new feature'`).
4.  Push to your branch (`git push origin feature/your-feature-name`).
5.  Create a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For more information or inquiries, feel free to reach out to the project owner:

Name: João Zanelato
Email: your-email@example.com
GitHub: [JoaoZanelato](https://github.com/JoaoZanelato)
