package com.project.movie.domain.enums;

public enum UserSearchResult {
    Not_initial_User_In_ES(1L, "Not initialize User in ES."),
    Initial_User_In_ES(2L, "Successfully initialize User in ES"),
    User_Not_In_ES(3L, "The user do not exist in ES."),
    User_Add_Success(4L, "Add user into ES Success."),
    User_Add_Fail(5L, "Add user into ES Fail."),
    User_Update_Success(6L, "Update user vector in ES Success."),
    User_Already_In_ES(8L, "User already existed in ES and has no difference with the incoming one."),
    User_Delete_Success(9L, "Delete user in ES Success."),
    User_Delete_Fail(10L, "Delete user in ES Fail."),
    User_Update_Fail(11L, "Update user vector in ES Fail.")
    ;


    private Long type;
    private String desc;
    UserSearchResult(Long type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public Long getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
