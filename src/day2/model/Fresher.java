/**
 *@author TuanBTD
 *@date Dec 27, 2019
 *@version version
 */

package day2.model;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class Fresher extends Candidate {
	private Date graduation_Date;
	private String graduation_Rank;
	private String education;

	public Fresher() {
		this.setCandidate_Type(1);
	}

	
	public Date getGraduation_Date() {
		return this.graduation_Date;
	}

	public void setGraduation_Date(Date graduation_date) {
		this.graduation_Date = graduation_date;
	}

	public String getGraduation_Rank() {
		return this.graduation_Rank;
	}

	public void setGraduation_Rank(String graduation_rank) {
		this.graduation_Rank = graduation_rank;
	}

	public String getEducation() {
		return this.education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public void setInfo() throws SQLException{
		System.out.println("<----- Nhap thong tin cho Fresher ----->");
		super.setInfo();
		Scanner sc = new Scanner(System.in);
		System.out.print("---> Nhap Graduation_date: ");
		Date Graduation_date = CandidateValiDate.enterDate("Graduation_date");
		this.setGraduation_Date(Graduation_date);
		System.out.print("---> Nhap Graduation_rank: ");
		String Graduation_Rank = sc.nextLine();
		this.setGraduation_Rank(Graduation_Rank);
		System.out.print("---> Nhap Education: ");
		String Education = sc.nextLine();
		this.setEducation(Education);
	}
	

	public void showMe() {
		System.out.println("Thong Tin ve Nhan Vien Fresher");
		System.out.print("CandidateType: "+this.getCandidate_Type()+ " -");
		super.ShowInfo();
		System.out.println("Graduation_date: "+Candidate.sdf.format(this.graduation_Date)+" -Graduation_rank: "+this.getGraduation_Rank()+" -Education: "+this.getEducation());
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}
}
