package com.example.asd.iamhere;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Group implements Parent<GroupUser>, Serializable {
    public String name;
    public LinkedList<GroupUser> users;

    public Group() {
        users = new LinkedList<>();
    }
    public Group(String name, LinkedList<GroupUser> users) {
        this.name = name;
        this.users = users;
    }

    public void addUser(GroupUser user) {
        users.add(user);
    }

    public void setUsers(LinkedList<GroupUser> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    @Override
    public List<GroupUser> getChildList() {
        return users;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
