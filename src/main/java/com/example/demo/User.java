package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int accountNumber;
	@Column
	private int pin;
	@Column
	private int openingBalance;
	@Column
	private int overdraft;
	
	public User() {	}

	public User(int accountNumber, int pin, int openingBalance, int overdraft) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.openingBalance = openingBalance;
		this.overdraft = overdraft;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public int getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(int openingBalance) {
		this.openingBalance = openingBalance;
	}

	public int getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(int overdraft) {
		this.overdraft = overdraft;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", accountNumber=" + accountNumber + ", pin=" + pin + ", openingBalance="
				+ openingBalance + ", overdraft=" + overdraft + "]";
	}
	
	
	
}
