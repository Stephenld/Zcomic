package ldh.com.zcomic.bean;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by allen liu on 2018/5/3.
 */

public class User extends BmobUser {
    private String userAvatarUrl;
    private String userNickName;
    private String userSex;
    private String userBirthday;
    private String userJob;
    private String userSchool;
    private String userArea;
    private String userMessage;
    public List<String> collect_comicId;

    public List<String> getCollect_comicId() {return collect_comicId;}

    public void setCollect_comicId(List<String> collect_comicId) {this.collect_comicId = collect_comicId;}

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
}

