/**
 *@author TuanBTD
 *@date Dec 27, 2019
 *@version version
 */

package day2.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;
import day2.Exception.BirthDayException;
import day2.Exception.EmailException;


public class CandidateValiDate {
	public static int enterInt() {
		int enter = 0;
		boolean check = false;
		do {
			try {
				enter = new Scanner(System.in).nextInt();
				check = true;
			}
			catch(Exception i) {
				System.out.println("Nhap sai dinh dang. Moi nhap kieu int: ");
			}
		} while (check == false);
		
		return enter;
	}
	
	public static String enterPhone() {
		String phone = null;
		boolean check = false;
		do {
			phone = new Scanner(System.in).nextLine();
			try {
				Integer.parseInt(phone);
				if(phone.length()!=10) {
					throw new Exception();			
				}
				check = true;
			}
			catch(Exception i) {
				System.out.print("Nhap sai dinh dang. Moi nhap lai so Phone: ");
			}
		} while (check == false);
		return phone;
	}
	
	public static int enterChoice(int soChucNang) {
		int choice = 0;
		boolean check = false;
		do {
				choice = enterInt();
				check = true;
				if(choice>soChucNang || choice < 1) {
					System.out.print("Khong co chuc nang nay. Moi lua chon lai chuc nang: ");
					check = false;
				}
		} while (check == false);
		
		return choice;
	}
	
	public static String checkEmail() {
		boolean check = false;
		String emailPattern = "\\w+@\\w+[.]\\w+";
		String email = null;
		do {
			email = new Scanner(System.in).nextLine();
			boolean check2 = email.matches(emailPattern);
			try {
				if(check2 == false) {
					throw new EmailException("Dinh dang email khong hop le");
				}
				check = true;
			}
			catch (EmailException i) {
				Candidate.logError.error(i);
				System.out.print("---> Nhap lai Email: ");
			}
			catch (Exception i) {
				Candidate.logError.error(i);
				System.out.print("---> Nhap lai Email: ");
			}

		} while (check == false);	
		return email;
	}

	public static Date enterDate(String message) {
		Date date = null;
		boolean check = false;
		do {
			String d = new Scanner(System.in).nextLine();
			try {
				date = Candidate.sdf.parse(d);
				String[] splitValues = d.split("/");
				if(splitValues.length == 3) {
					int day = Integer.parseInt(splitValues[0]);
					int month = Integer.parseInt(splitValues[1]);
					int year = Integer.parseInt(splitValues[2]);
					LocalDate.of(year, month, day);
				}
				check = true;
			}
			catch (Exception e) {
				Candidate.logError.error(e);
				System.out.print("Nhap lai "+message+" :");
			}
		} while (check == false);

		return date;
	}

	public static Date enterBirthday() {
		boolean check = false;
		Date birthday = null;
		int year2 = LocalDateTime.now().getYear();
		do {
			birthday = enterDate("Birthday");
			LocalDate dateToLocalDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int year = dateToLocalDate.getYear();
			try {
				if(year<1900 || year>year2) {
					throw new BirthDayException("Nam sinh phai nam trong khoang 1900 den nam "+year2);
				}
				check = true;
			}
			catch(BirthDayException i) {
				Candidate.logError.error(i);
				System.out.print("---> Nhap lai BirthDay: ");
			}
			catch(Exception i) {
				Candidate.logError.error(i);
				System.out.print("---> Nhap lai BirthDay: ");
			}
		} while(check == false);
		return birthday;
	}
}
