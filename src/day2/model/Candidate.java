/**
 *@author TuanBTD
 *@date Dec 27, 2019
 *@version version
 */

package day2.model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import org.apache.log4j.Logger;
import day2.DAO.CandidateDAO;


public abstract class Candidate implements Comparable<Candidate> {
	public static int candidate_Count = 0;
	private int candidate_Type;
	private String candidateID;
	private String fullName;
	private Date birthDay;
	private String phone;
	private String email;
	private ArrayList<Certificated> certificated_List;
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static final Logger logError = Logger.getLogger("ErrorFile");
	
	public Candidate() {
	}

	public int getCandidate_Type() {
		return this.candidate_Type;
	}

	public void setCandidate_Type(int candidateType) {
		this.candidate_Type = candidateType;
	}

	public String getCandidateID() {
		return this.candidateID;
	}

	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDayValiDate() {
		this.birthDay = CandidateValiDate.enterBirthday();
	}
	
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setEmailValiDate() {
		this.email = CandidateValiDate.checkEmail();
	}

	public ArrayList<Certificated> getList_Certificated() {
		return certificated_List;
	}

	public void setList_Certificated(ArrayList<Certificated> list_Certificated) {
		this.certificated_List = list_Certificated;
	}

	public void setInfo() throws SQLException{
		Scanner sc = new Scanner(System.in);

		this.setCandidateID(CandidateDAO.idCDNew());  //tu dong tang CandidateID

		System.out.print("---> Nhap FullName: ");
		String name = sc.nextLine();
		this.setFullName(name);

		System.out.print("---> Nhap BirthDay: ");
		this.setBirthDayValiDate();

		System.out.print("---> Nhap Phone: ");
		String phone = CandidateValiDate.enterPhone();
		this.setPhone(phone);

		System.out.print("---> Nhap Email: ");
		this.setEmailValiDate();

		System.out.print("---> Ung vien co bang cap khong (1. Co  2. Khong): ");
		int check = CandidateValiDate.enterChoice(2);

		if(check ==1){
			System.out.print("---> Ung vien co bao nhieu bang cap: ");
			int count = CandidateValiDate.enterInt();
			if(count>0) {
				certificated_List = new ArrayList<Certificated>();
			}

			int t=1;
			while(count>0){
				System.out.println("---> Nhap thong tin bang cap thu "+t);
				Certificated certificated = new Certificated();
				
				System.out.print("---> Nhap CertificatedID: ");
				String CertificatedID = sc.nextLine();
				certificated.setCertificatedID(CertificatedID);

				System.out.print("---> Nhap CertificateName: ");
				String CertificateName = sc.nextLine();
				certificated.setCertificateName(CertificateName);

				System.out.print("---> Nhap CertificateRank: ");
				String CertificateRank = sc.nextLine();
				certificated.setCertificateRank(CertificateRank);

				System.out.print("---> Nhap CertificatedDate: ");
				Date CertificatedDate = CandidateValiDate.enterDate("CertificatedDate");
				certificated.setCertificatedDate(CertificatedDate);

				certificated_List.add(certificated);
				count--;
				t++;
			}
		}
	}

	public abstract void showMe();
	public final void ShowInfo() {
		System.out.println("Id: "+this.candidateID+" -Name: "+this.fullName+" -BirthDay: "+sdf.format(this.birthDay)+" -Phone: "+this.phone+" -Email: "+this.email);
		if(certificated_List.size() > 0){
			System.out.println("Gom nhung bang cap sau day: ");
			int j =1;
			for(Certificated i : this.certificated_List){
				System.out.print("Thong tin bang cap "+j+" : ");
				System.out.println("CertificatedID: "+i.getCertificatedID()+" -CertificatedName: "+i.getCertificateName()
				+" -CertificatedRank: "+i.getCertificateRank()+" -CertificatedDate: "+sdf.format(i.getCertificatedDate()));
				j++;
			}
		}
	}

	public int compareTo(Candidate cd) {
		if(this.getCandidate_Type()!=cd.getCandidate_Type()){
			return Integer.valueOf(this.candidate_Type).compareTo(Integer.valueOf(cd.candidate_Type));
		} else{
			return Long.valueOf(this.birthDay.getTime()).compareTo(Long.valueOf(cd.birthDay.getTime()));
		}
	}
}
