package com.example.demo;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {

		private AtmService atmService;
		
		@Autowired
		public Controller(AtmService atmService) {
			this.atmService = atmService;
		}
		
		
		@RequestMapping("/")
		public String home() {
			return "index.jsp";
		}

		
		
		@PostMapping("/Checkbalance")  //@ResponseBody
		public String checkbalance() {
			return "checkBalanceForm.jsp";
		}
		
		@PostMapping("/showbalance")  //@ResponseBody
		public String showbalance(@RequestParam("accountnumberEntered") int accountnumberEntered,
								  @RequestParam("pinEntered") int pinEntered,
								  HttpSession session) throws SQLException {
			String balance = atmService.fetchBalance(accountnumberEntered,pinEntered);
			
			session.setAttribute("showbalanceString", balance);
			return "showbalance.jsp";
		}
		
		@PostMapping("/Withdraw")  //@ResponseBody
		public String Withdraw() {
			return "withdrawform.jsp";
		}
		
		@PostMapping("/afterwithdraw")  //@ResponseBody
		public String showbalanceafterwithdraw(@RequestParam("accountnumberEntered") int accountnumberEntered,
								  				@RequestParam("pinEntered") int pinEntered,
								  				@RequestParam("withdrawamountEntered") int withdrawamountEntered,
								  				HttpSession session) throws SQLException {
			
			
			String balance = atmService.withdrawBalance(accountnumberEntered,pinEntered,withdrawamountEntered);
			
			session.setAttribute("showbalanceafterwithdraw", balance);
			return "afterwithdraw.jsp";
		}
		
		
		
		
		
		
		
		
		
		
}
