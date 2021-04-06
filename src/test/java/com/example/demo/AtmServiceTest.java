package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AtmServiceTest {

	@MockBean 
	private UserRepo userRepo;
	
	@InjectMocks
	private AtmService atmService ;
	
	@Test
	void testFetchBalance() {
		User rs= new User(0,0,0,0);
			
		Mockito.when(userRepo.findByAccountNumber(0)).thenReturn(rs);
		assertThat(atmService.fetchBalance(0, 0)).isEqualTo("Balance available : " + rs.getOpeningBalance()+"\n Overdraft limit : " +rs.getOverdraft());
	}
	
	@Test
	void testFetchBalanceInvalidAccountnumber() {
		User rs= new User(0,0,0,0);
			
		Mockito.when(userRepo.findByAccountNumber(0)).thenReturn(rs);
		assertThat(atmService.fetchBalance(1, 0)).isEqualTo("Invalid Account number entered");
	}
	
	@Test
	void testFetchBalanceInvalidPin() {
		User rs= new User(0,0,0,0);
			
		Mockito.when(userRepo.findByAccountNumber(0)).thenReturn(rs);
		assertThat(atmService.fetchBalance(0, 1)).isEqualTo("Incorrect Pin entered");
	}

	@Test
	void testWithdrawBalanceInvalidAccountNumber() {
		User rs= new User(100,100,100,100);
		
		Mockito.when(userRepo.findByAccountNumber(100)).thenReturn(rs);
		assertThat(atmService.withdrawBalance(1,100,5)).isEqualTo("Invalid Account number entered"  +" Trasaction couldn't complete");
	}
	
	@Test
	void testWithdrawBalanceValidAccountnumber() {
		User rs= new User(100,100,100,100);
		
		Mockito.when(userRepo.findByAccountNumber(100)).thenReturn(rs);
		assertThat(atmService.withdrawBalance(100,100,5)).isEqualTo("Dispensed: \n       50Note = "+"0"+"\n, 20Note = "+"0"+"\n, 10Note = "+"0"+ "\n, 5Note = "+"1");            
	}
	
	@Test
	void testWithdrawBalanceInvalidPin() {
		User rs= new User(100,100,100,100);
		
		Mockito.when(userRepo.findByAccountNumber(100)).thenReturn(rs);
		assertThat(atmService.withdrawBalance(100,11,5)).isEqualTo("Incorrect Pin entered." +" Trasaction couldn't complete");
	}

}







