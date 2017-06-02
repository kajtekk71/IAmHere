package com.example.asd.iamhere;

import java.io.Serializable;

public class GroupUser implements Serializable {
    public String name;
    public String surname;
    public String nickname;
    public String phoneNo;
    public String email;

    public GroupUser() {

    }

    public GroupUser(String name, String surname, String nickname, String phoneNo, String email) {
        this.name = name == null ? "" : name;
        this.surname = surname == null ? "" : surname;
        this.nickname = nickname == null ? "" : nickname;
        this.phoneNo = phoneNo == null ? "" : phoneNo;
        this.email = email == null ? "" : email;
    }

    public void modifyName(String name) {
        this.name = name;
    }

    public void modifySurname(String surname) {
        this.surname = surname;
    }

    public void modifyNickname(String nickname) {
        this.nickname = nickname;
    }

    public void modifyPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void modifyEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return "Nickname: " + nickname + "\n" + "Name: " + name + "\n" + "Surname: " + surname + "\n" + "Phone: " + phoneNo + "\n" + "Email: " + email + "\n";
    }
}
