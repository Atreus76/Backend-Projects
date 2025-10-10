package com.example.real_time_polling.client;

public class VoteTally {
    private int optionA;
    private int optionB;

    public VoteTally() {}

    public VoteTally(int optionA, int optionB) {
        this.optionA = optionA;
        this.optionB = optionB;
    }

    public int getOptionA() {
        return optionA;
    }

    public void setOptionA(int optionA) {
        this.optionA = optionA;
    }

    public int getOptionB() {
        return optionB;
    }

    public void setOptionB(int optionB) {
        this.optionB = optionB;
    }

    @Override
    public String toString() {
        return "VoteTally{" +
                "optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                '}';
    }
}
