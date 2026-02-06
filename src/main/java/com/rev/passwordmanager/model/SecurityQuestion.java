package com.rev.passwordmanager.model;

public class SecurityQuestion {
    private int id;
    private int userId;
    private String question;
    private String answer; // hashed

    public SecurityQuestion() {}

    public SecurityQuestion(int userId, String question, String answer) {
        this.userId = userId;
        this.question = question;
        this.answer = answer;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}
