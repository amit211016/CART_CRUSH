package com.ecom.user.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String mobileNo;
    private String email;
    private String address;
    private String state;
    private String city;
    private String pinCode;
    private String password;
    private String imageName;
    private String role;
    private Boolean enabled;
    private Boolean accountNonLocked;
    private Integer failedAttempt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Boolean getEnabled() { return enabled; }
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }
    public Boolean getAccountNonLocked() { return accountNonLocked; }
    public void setAccountNonLocked(Boolean accountNonLocked) { this.accountNonLocked = accountNonLocked; }
    public Integer getFailedAttempt() { return failedAttempt; }
    public void setFailedAttempt(Integer failedAttempt) { this.failedAttempt = failedAttempt; }
    public Date getLockTime() { return lockTime; }
    public void setLockTime(Date lockTime) { this.lockTime = lockTime; }
}
