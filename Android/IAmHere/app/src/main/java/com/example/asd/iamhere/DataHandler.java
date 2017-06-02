package com.example.asd.iamhere;

public interface DataHandler {
    void addNewUser(int position, GroupUser user);

    void replaceUser(GroupUser user, int parent, int child);

    void addNewGroup(Group group);

    void editName(Group group, int position);
}
