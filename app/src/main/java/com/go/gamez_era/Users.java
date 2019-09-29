package com.go.gamez_era;

/**
 * Created by anubhav on 09-12-2018.
 */

public class Users
{
    private String wallet_amount;

    private String user_name;
    private String total_winning;
    private String total_kills;
    private String likes;

    private String phone;
    public Users() {
    }
    public Users(String phone,String wallet_amount,String user_name,String total_winning,String total_kills,String likes) {
        this.wallet_amount = wallet_amount;
        this.user_name=user_name;
        this.total_winning=total_winning;
        this.total_kills=total_kills;
        this.likes=likes;
        this.phone=phone;


    }
    public String getWallet_amount() {
        return wallet_amount;
    }
    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }
   public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getTotal_winning() {
        return total_winning;
    }
    public String getTotal_kills() {
        return total_kills;
    }
    public void setTotal_kills(String total_kills) { this.total_kills = total_kills; }
    public void setTotal_winning(String total_winning) {
        this.total_winning = total_winning;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}