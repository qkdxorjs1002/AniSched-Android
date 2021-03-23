package com.novang.anisched.model.anissia;

public class AutoCorrect {

    private int id;
    private String subject;

    public AutoCorrect(String string) {
        String[] array = string.split(" ", 2);

        setId(Integer.parseInt(array[0]));
        setSubject(array[1]);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
