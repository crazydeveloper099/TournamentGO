package com.go.gamez_era;

/**
 * Created by anubhav on 15-12-2018.
 */

public class Cardjava
{
    private String match_number,status,version,team,device,prizes,fst,scnd,third,date,time,per_kill,description,fst_amt,scnd_amt,third_amt,date_value,time_value,per_kill_value,image,spots,
            pk_special;
    public Cardjava()
    {

    }
    public Cardjava(String status,String prizes, String fst, String scnd, String third, String date, String time, String per_kill, String description, String fst_amt, String scnd_amt, String third_amt, String date_value, String time_value, String per_kill_value, String image, String spots,String team,String device,String version,String match_number,String pk_special) {
        this.prizes = prizes;
        this.fst = fst;
        this.version=version;
        this.scnd = scnd;
        this.third = third;
        this.date = date;
        this.time = time;
        this.pk_special=pk_special;
        this.per_kill = per_kill;
        this.description = description;
        this.fst_amt = fst_amt;
        this.scnd_amt = scnd_amt;
        this.third_amt = third_amt;
        this.date_value = date_value;
        this.time_value = time_value;
        this.per_kill_value = per_kill_value;
        this.image = image;
        this.spots=spots;
        this.team=team;
        this.status=status;
        this.device=device;
        this.match_number=match_number;
    }
    public String getPrizes() {
        return prizes;
    }

    public void setPrizes(String prizes) {
        this.prizes = prizes;
    }

    public String getFst() {
        return fst;
    }

    public void setFst(String fst) {
        this.fst = fst;
    }

    public String getScnd() {
        return scnd;
    }

    public void setScnd(String scnd) {
        this.scnd = scnd;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPer_kill() {
        return per_kill;
    }

    public void setPer_kill(String per_kill) {
        this.per_kill = per_kill;
    }

    public String getVersion() {
        return version;
    }


    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPk_special() {
        return pk_special;
    }

    public void setPk_special(String pk_special) {
        this.pk_special = pk_special;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFst_amt() {
        return fst_amt;
    }

    public void setFst_amt(String fst_amt) {
        this.fst_amt = fst_amt;
    }

    public String getScnd_amt() {
        return scnd_amt;
    }

    public void setScnd_amt(String scnd_amt) {
        this.scnd_amt = scnd_amt;
    }

    public String getThird_amt() {
        return third_amt;
    }

    public void setThird_amt(String third_amt) {
        this.third_amt = third_amt;
    }




    public String getDate_value() {
        return date_value;
    }

    public void setDate_value(String date_value) {
        this.date_value = date_value;
    }

    public String getTime_value() {
        return time_value;
    }

    public void setTime_value(String time_value) {
        this.time_value = time_value;
    }

    public String getPer_kill_value() {
        return per_kill_value;
    }

    public void setPer_kill_value(String per_kill_value) {
        this.per_kill_value = per_kill_value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSpots() {
        return spots;
    }

    public void setSpots(String spots) {
        this.spots = spots;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getMatch_number() {
        return match_number;
    }

    public void setMatch_number(String match_number) {
        this.match_number = match_number;
    }
}
