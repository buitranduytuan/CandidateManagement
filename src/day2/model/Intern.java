/**
 *@author TuanBTD
 *@date Dec 27, 2019
 *@version version
 */

package day2.model;

import java.sql.SQLException;
import java.util.Scanner;

public class Intern extends Candidate {
	private String majors;
	private int semester;
	private String university_Name;

	public Intern() {
		this.setCandidate_Type(2);
	}

	public Intern(String majors, int semester, String university_name) {
		super();
		this.majors = majors;
		this.semester = semester;
		this.university_Name = university_name;
	}

	public String getMajors() {
		return this.majors;
	}

	public void setMajors(String majors) {
		this.majors = majors;
	}

	public int getSemester() {
		return this.semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getUniversity_name() {
		return this.university_Name;
	}

	public void setUniversity_name(String university_name) {
		this.university_Name = university_name;
	}

	public void setInfo() throws SQLException{
		System.out.println("<----- Nhap thong tin cho Intern ----->");
		super.setInfo();
		Scanner sc = new Scanner(System.in);
		System.out.print("---> Nhap Majors: ");
		String majors = sc.nextLine();
		this.setMajors(majors);
		System.out.print("---> Nhap Semester: ");
		int semester = CandidateValiDate.enterInt();
		this.setSemester(semester);
		System.out.print("---> Nhap University_name: ");
		String university_name = sc.nextLine();
		this.setUniversity_name(university_name);
	}
	
	public void showMe() {
		System.out.println("Thong Tin ve Nhan Vien Intern");
		System.out.print("CandidateType: "+this.getCandidate_Type()+ " -");
		super.ShowInfo();
		System.out.println("Majors: "+this.getMajors()+" -Semester: "+this.getSemester()+" -University_name: "+this.getUniversity_name());
		System.out.println("-----------------------------------------------------------------------------------------------------");
	}
}
