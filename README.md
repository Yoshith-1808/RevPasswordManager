# 🔐 RevPassword Manager

## 📝 Application Overview
The **Password Manager** is a secure console-based application that allows users to safely store and manage passwords for their various online accounts. Users can create an account with a master password, log in, generate strong random passwords, store account credentials, and manage their password vault.  
The application emphasizes security through **password encryption** and **master password protection**.

---

## ⚙️ Core Functional Features

- Create an account to securely manage passwords
- Log in to access the application
- Update profile information like username and email
- Generate strong random passwords
- List all stored passwords with account names
- View individual password details (requires master password re-entry)
- Add, update, and delete password entries
- Search passwords by account name
- Add and manage security questions for account recovery
- Get verification codes for sensitive operations

---

## ✅ Standard Functional Scope

- Registered users can:
    - Log in
    - Change master password
    - Recover forgotten passwords via security questions

---

## 💻 Environment / Technologies

- **Programming Language:** Java
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **Testing:** JUnit 5
- **Logging:** Log4J

---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone <your-repo-url>
   

Import the project into IntelliJ IDEA or any Java IDE

Ensure MySQL is running and the database revpassword_manager exists

Update DBConnection.java with your MySQL credentials

Build the project with Maven:

```bash
    mvn clean install
   ```

Run the application:
```bash
    mvn exec:java -Dexec.mainClass="com.rev.passwordmanager.ui.Main"
```

Run tests with:
```bash
    mvn test
```

## 📂 Project Structu

```bash
RevPasswordManager/
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ com/rev/passwordmanager/
│  │  │     ├─ dao/           # Database access objects
│  │  │     │  ├─ PasswordEntryDAO.java
│  │  │     │  ├─ SecurityQuestionDAO.java
│  │  │     │  ├─ UserDAO.java
│  │  │     │  └─ VerificationCodeDAO.java
│  │  │     ├─ model/         # User, PasswordEntry, SecurityQuestion models
│  │  │     │  ├─ PasswordEntry.java
│  │  │     │  ├─ SecurityQuestion.java
│  │  │     │  ├─ User.java
│  │  │     │  └─ VerificationCode.java
│  │  │     ├─ service/       # Business logic (UserService, OTPService, etc.)
│  │  │     │  ├─ OTPService.java
│  │  │     │  ├─ PasswordService.java
│  │  │     │  ├─ SecurityQuestionService.java
│  │  │     │  └─ UserService.java
│  │  │     ├─ ui/            # Console UI
│  │  │     │  ├─ Main.java
│  │  │     │  ├─ MainMenu.java
│  │  │     │  └─ TestDB.java
│  │  │     └─ Util/          # Helper utilities
│  │  │        ├─ DBConnection.java
│  │  │        ├─ EncryptionUtil.java
│  │  │        ├─ HashUtil.java
│  │  │        ├─ OTPUtil.java
│  │  │        └─ PasswordGenerator.java
│  │  └─ resources/           # Configs, properties, etc.
│  └─ test/
│     └─ java/com/rev/passwordmanager/
│        ├─ PasswordServiceTest.java
│        ├─ SecurityQuestionServiceTest.java
│        └─ UserServiceTest.java
└─ pom.xml

```

## 🔒 Security Highlights

- Master password is hashed using SHA-256

- Stored passwords are encrypted using AES

- OTP verification for sensitive operations

- Security questions for account recovery



    