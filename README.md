# Banking System (Java)

A simple menu-driven console banking application written in Java.

## Features
- Create a bank account with an automatically generated account number
- Deposit money into an existing account
- Withdraw money with an insufficient-funds check
- Check account holder details and current balance
- Validate user input and handle invalid operations
- Store accounts in a `HashMap` during the program session

## Concepts Used
- Object-oriented programming (classes and encapsulation)
- `HashMap` and the `Map` interface
- Exception handling and a custom `InsufficientFundsException`
- `Scanner` for console input
- Loops, conditional statements, and switch expressions

## Requirements
- Java 14 or later (the program uses switch arrow syntax)

## How to Run

From the repository root:

```bash
javac src/BankApp.java
java -cp src BankApp
```

## Project Structure

```text
Banking-System-Java/
├── README.md
├── .gitignore
└── src/
    └── BankApp.java
```

## Limitations
- Account data is stored in memory and is cleared when the program exits.
- This is an educational console project, not a real banking system.
- It does not include database storage, authentication, encryption, or a graphical interface.

## Author
Raviraj Ambadas Babar
