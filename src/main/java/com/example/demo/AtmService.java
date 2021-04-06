package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AtmService {

	
	private int NotesOf50 = 10;
	private int NotesOf20 = 30;
	private int NotesOf10 = 30;
	private int NotesOf5 = 20;
	
	private UserRepo userRepo;

	@Autowired
	public AtmService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	
	
	/*
	 * public AtmService(int notesOf50, int notesOf20, int notesOf10, int notesOf5)
	 * { NotesOf50 = notesOf50; NotesOf20 = notesOf20; NotesOf10 = notesOf10;
	 * NotesOf5 = notesOf5; }
	 */



	public int getNotesOf50() {
		return NotesOf50;
	}



	public void setNotesOf50(int notesOf50) {
		NotesOf50 = notesOf50;
	}



	public int getNotesOf20() {
		return NotesOf20;
	}



	public void setNotesOf20(int notesOf20) {
		NotesOf20 = notesOf20;
	}



	public int getNotesOf10() {
		return NotesOf10;
	}



	public void setNotesOf10(int notesOf10) {
		NotesOf10 = notesOf10;
	}



	public int getNotesOf5() {
		return NotesOf5;
	}



	public void setNotesOf5(int notesOf5) {
		NotesOf5 = notesOf5;
	}



	@EventListener
	public void eventListener(ApplicationStartedEvent event) throws SQLException {
		System.out.println("Initializing database with user");
		
		
		
		User result= new User(0,0,0,0);
		
		result = userRepo.findByAccountNumber(123456789);
		if(!(result.getAccountNumber()==123456789))
		{
			System.out.println("Adding in databse user:"+123456789);
			userRepo.save(new User(123456789,1234,800,200));
		}
		
		User result2= new User(0,0,0,0);
		result2 = userRepo.findByAccountNumber(987654321);
		if(!(result2.getAccountNumber()==987654321))
		{   System.out.println("Adding in databse user:"+987654321);
			userRepo.save(new User(987654321,4321,1230,150));
		}
		
		
	}



	public String fetchBalance(int accountnumberEntered, int pinEntered)  {
		
		User rs= new User(0,0,0,0);
		rs =  userRepo.findByAccountNumber(accountnumberEntered);
		
		if(rs!=null )
		{
			if(rs.getPin()==pinEntered)
			{
				return "Balance available : " + rs.getOpeningBalance()+"\n Overdraft limit : " +rs.getOverdraft() ;
			}
			else
				return "Incorrect Pin entered";
			
		}
		else
			return "Invalid Account number entered";

	}



	public String withdrawBalance(int accountnumberEntered, int pinEntered, int withdrawamountEntered) {
		
		User rs= new User(0,0,0,0);
		rs =  userRepo.findByAccountNumber(accountnumberEntered);
		int withdrawamount=withdrawamountEntered;
		
		
		if(rs!=null )
		{
			if(rs.getPin()==pinEntered)
			{
				int balance=rs.getOpeningBalance();
				int overdraft = rs.getOverdraft();
				
				if(withdrawamountEntered<=balance) 
				{
					int noteValue[]= {50,20,10,5};
					int noteCount[]= {10,30,30,20};   //array[0] denotes NotesOf50
					int dispensedNote[]= {0,0,0,0};
					if(withdrawamountEntered>0)
					{
						for(int i=0;i<4;i++)
						{
							int check=withdrawamountEntered/noteValue[i];
							if(check<=noteCount[i])
							{
								dispensedNote[i]=withdrawamountEntered/noteValue[i];
								withdrawamountEntered=withdrawamountEntered%noteValue[i];
								noteCount[i]=noteCount[i]-dispensedNote[i];
							}
							else 
							{
								withdrawamountEntered=withdrawamountEntered-noteCount[i]*noteValue[i];
								dispensedNote[i]=noteCount[i];
								noteCount[i]=0;
								
							}
						}
						
					}
					
					
					if(withdrawamountEntered==0)
					{   NotesOf50 = dispensedNote[0];
						NotesOf20 = dispensedNote[1];
						NotesOf10 = dispensedNote[2];
						NotesOf5  = dispensedNote[3];
						
						
						rs.setOpeningBalance(balance-withdrawamount);
						userRepo.save(rs);
						
										return "Dispensed: \n       50Note = "+dispensedNote[0]
												+"\n, 20Note = "+dispensedNote[1]
														+"\n, 10Note = "+dispensedNote[2]
																+ "\n, 5Note = "+dispensedNote[3];
					}
					else
						return "Not able to dispense as exact Notes not avaialble to dispense"+" Trasaction couldn't complete";
				}
				else if(withdrawamountEntered<=(balance+overdraft))
				{//////////////////

								int noteValue[]= {50,20,10,5};
								int noteCount[]= {10,30,30,20};   //array[0] denotes NotesOf50
								int dispensedNote[]= {0,0,0,0};
								if(withdrawamountEntered>0)
								{
									for(int i=0;i<4;i++)
									{
										int check=withdrawamountEntered/noteValue[i];
										if(check<=noteCount[i])
										{
											dispensedNote[i]=withdrawamountEntered/noteValue[i];
											withdrawamountEntered=withdrawamountEntered%noteValue[i];
											noteCount[i]=noteCount[i]-dispensedNote[i];
										}
										else 
										{
											withdrawamountEntered=withdrawamountEntered-noteCount[i]*noteValue[i];
											dispensedNote[i]=noteCount[i];
											noteCount[i]=0;
											
										}
									}
									
								}
								
								
								if(withdrawamountEntered==0)
								{   NotesOf50 = dispensedNote[0];
									NotesOf20 = dispensedNote[1];
									NotesOf10 = dispensedNote[2];
									NotesOf5  = dispensedNote[3];
									
									
									rs.setOpeningBalance(0);
									rs.setOverdraft(balance+overdraft-withdrawamount);
									
									userRepo.save(rs);
									
													return "Dispensed: \n       50Note = "+dispensedNote[0]
															+"\n, 20Note = "+dispensedNote[1]
																	+"\n, 10Note = "+dispensedNote[2]
																			+ "\n, 5Note = "+dispensedNote[3]
																					+"   Used overdraft = "+(withdrawamount-balance);
								}
								else
									return "Not able to dispense as exact Notes not avaialble to dispense"+" Trasaction couldn't complete";
				}////////////////
				else
					return "Withdraw amount is more than balance and overdraft combined. " +" Trasaction couldn't complete";
				 
				//return "Balance available : " + rs.getOpeningBalance()+"\n Overdraft limit : " +rs.getOverdraft() ;
			}
			else
				return "Incorrect Pin entered." +" Trasaction couldn't complete";
			
		}
		else
			return "Invalid Account number entered"  +" Trasaction couldn't complete";
	}
	
}
