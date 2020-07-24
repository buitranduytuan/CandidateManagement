/**
 *@author TuanBTD
 *@date Dec 27, 2019
 *@version version
 */

package day2.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import day2.DAO.CandidateDAO;
import day2.model.Candidate;
import day2.model.Experience;
import day2.model.Fresher;



public class QuanLyCandidate {
	public void menu() throws SQLException {
		boolean check2 = false;
		do{
			System.out.println("\t ------------ Chuong trinh quan ly Candidate ------------");
			System.out.print("1. Nhap thong tin Candidate \t");
			System.out.print("2. Xuat thong tin Candidate \t");
			System.out.println("3. Update thong tin Candidate theo CandidateID");
			System.out.print("---> Moi lua chon chuc nang: ");
			int check = CandidateValiDate.enterChoice(3);

			if(check==1){
				inputCandidate();
				System.out.println("Ban da vua them "+Candidate.candidate_Count+" Candidate vao DataBase");
			}
			if(check ==2) {
				showInforCandidate();
			}
			if(check ==3) {
				CandidateDAO.updateInfoNoSql();
			}

			System.out.print("Ban co muon tiep tuc chuong trinh: 1. Co   2. Khong \t");
			System.out.println("---> Lua chon: ");
			int c = CandidateValiDate.enterChoice(2);
			if(c == 1){
				check2 = true;
			}
			if(c == 2){
				check2 = false;
				System.out.println("<----- Ket thuc chuong trinh!!! ----->");
			}
		} while(check2);
	}

	public void inputCandidate() throws SQLException {
		Candidate cd;
		boolean check = false;

		do {
			System.out.println("\t ------------ Nhap thong tin cho Candidate ------------");
			System.out.print("1- Experience \t 2- Fresher \t 3- Intern \t---> Chon Loai Candidate: ");
			
			int check2 = CandidateValiDate.enterChoice(2);
				switch (check2) {
				case 1: 
					cd = new Experience();
					cd.setInfo();
					CandidateDAO.insertCadidateToDBNoSql(cd);
					break;
				case 2: 
					cd = new Fresher();
					cd.setInfo();
					CandidateDAO.insertCadidateToDBNoSql(cd);
					break;
				case 3: 
					cd = new Intern();
					cd.setInfo();
					CandidateDAO.insertCadidateToDBNoSql(cd);
					break;
				}
		} while(check);
	}

	public void showInforCandidate() throws SQLException {
		System.out.println("\t ------------ Xuat thong tin cua cac Candidate: ------------");
		System.out.print("1. Xuat thong tin cua cac Experience \t");
		System.out.println("2. Xuat thong tin cua cac Fresher");
		System.out.print("3. Xuat thong tin cua cac Intern \t");
		System.out.println("4. Xuat ten tat ca Candidate");
		System.out.print("5. Xuat tat ca thong tin Candidate \t");
		System.out.println("6. Sap xep tat ca thong tin Candidate theo CandidateType & Nam sinh");
		System.out.print("---> Moi lua chon chuc nang: ");
		int check = CandidateValiDate.enterChoice(6);
		System.out.println("-----------------------------------------------------------------------------------------------------");

		ArrayList<Candidate> candidate_List = CandidateDAO.getListCandidateFromDB();

		if(check ==1){
			for(Candidate i: candidate_List){
				if(i  instanceof Experience){
					i.showMe();
				}
			}
		} 
		if(check ==2){
			for(Candidate i: candidate_List){
				if(i  instanceof Fresher){
					i.showMe();
				}
			}
		}
		if(check ==3){
			for(Candidate i: candidate_List){
				if(i  instanceof Intern){
					i.showMe();
				}
			}
		}
		if(check ==4){
			CandidateDAO.getNameAllCandidate(candidate_List);
		}
		if(check ==5){
			for(Candidate i: candidate_List){
				i.showMe();
			}
		}
		if(check ==6){
			Collections.sort(candidate_List);
			for(Candidate i: candidate_List) {
				i.showMe();
			}
		}
	}
}

