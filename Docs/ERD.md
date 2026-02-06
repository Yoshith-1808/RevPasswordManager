
### Relationship Summary Diagram (Text + Symbols)
```text
 +-------------------+          +--------------------+
|       User        |1        N|   PasswordVault    |
+-------------------+----------+--------------------+
| user_id (PK)      |          | password_id (PK)   |
| name              |          | user_id (FK)       |
| email             |          | account_name       |
| master_password_h |          | username           |
| created_at        |          | password_encrypted |
| updated_at        |          | url                |
+-------------------+          | notes              |
                               | created_at         |
                               | updated_at         |
                               +--------------------+

          1
          |
          |N
+-------------------+
| SecurityQuestion  |
+-------------------+
| question_id (PK)  |
| user_id (FK)      |
| question_text     |
| answer_hash       |
| created_at        |
+-------------------+

          1
          |
          |N
+-------------------+
| VerificationCode  |
+-------------------+
| code_id (PK)      |
| user_id (FK)      |
| code              |
| expires_at        |
| used              |
| created_at        |
+-------------------+

```

### 
🔑 Relationships Summary

```text
### Relationships Summary
----------------------------------------------------------------------------------------------------------------|
| Relationship             | Type | Description                                                                  |
|------------------------- |------|----------------------------------------------------------------------------- |
| User → PasswordVault     | 1:N  | A user can have multiple saved passwords in their vault.                    |
| User → SecurityQuestion  | 1:N  | A user can set multiple security questions for account recovery.            |
| User → VerificationCode  | 1:N  | A user can generate multiple verification codes for sensitive operations.   |
----------------------------------------------------------------------------------------------------------------|
```