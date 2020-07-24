/**
 *@author TuanBTD
 *@date Dec 27, 2019
 *@version version
 */

package day2.model;

import java.sql.SQLException;
import java.util.Scanner;

public class Experience extends Candidate  {
	private int expInYear;
	private String proSkill;
	
	public Experience() {
		this.setCandidate_Type(0);
	}
	
	public int getExpInYear() {
		return this.expInYear;
	}
	public void setExpInYear(int expInYear) {
		this.expInYear = expInYear;
	}
	public String getProSkill() {
		return this.proSkill;
	}
	public void setProSkill(String proSkill) {
		this.proSkill = proSkill;
	}
	
	public void setInfo() throws SQLException{
		System.out.println("<----- Nhap thong tin cho Experience ----->");
		super.setInfo();
		Scanner sc = new Scanner(System.in);
		System.out.print("---> Nhap ExpInYear: ");
		int ExpInYear = CandidateValiDate.enterInt();
		this.setExpInYear(ExpInYear);
		System.out.print("---> Nhap ProSkill: ");
		String ProSkill = sc.nextLine();
		this.setProSkill(ProSkill);
	}
	
	public void showMe(){
		System.out.println("Thong Tin ve Nhan Vien Experience");
		System.out.print("CandidateType: "+this.getCandidate_Type()+ " -");
		super.ShowInfo();
		System.out.println("ExpInYear: "+this.getExpInYear()+" -ProSkill: "+this.getProSkill());
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}
}
