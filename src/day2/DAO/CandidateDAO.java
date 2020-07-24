/**
 *@author TuanBTD
 *@date Dec 30, 2019
 *@version version
 */

package day2.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import day2.model.Candidate;
import day2.model.CandidateValiDate;
import day2.model.Certificated;
import day2.model.Experience;
import day2.model.Fresher;
import day2.model.Intern;
import day2.utils.ConnectionDB;

public class CandidateDAO {
	public static final Logger logInfo = Logger.getLogger("InfoFile");
	public static java.sql.Date convertToSQLDate(java.util.Date javaDate) {
		java.sql.Date sqlDate = null;
		if (javaDate != null) {
			sqlDate = new Date(javaDate.getTime());
		}
		return sqlDate;
	}

	public static void insertCadidateToDB(Candidate cd) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionDB.getMyConnect();
			String sql = "INSERT INTO Candidate VALUES(?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cd.getCandidateID());
			ps.setInt(2, cd.getCandidate_Type());
			ps.setString(3, cd.getFullName());
			ps.setDate(4, convertToSQLDate(cd.getBirthDay()));
			ps.setString(5, cd.getPhone());
			ps.setString(6, cd.getEmail());
			ps.execute();
			if(cd.getList_Certificated()!=null){
				List<Certificated> ceList = cd.getList_Certificated();
				for(int i=0; i<ceList.size(); i++){
					String sql2 = "INSERT INTO Certificated VALUES(?, ?, ?, ?, ?)";
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, ceList.get(i).getCertificatedID());
					ps2.setString(2, ceList.get(i).getCertificateName());
					ps2.setString(3, ceList.get(i).getCertificateRank());
					ps2.setDate(4, convertToSQLDate(ceList.get(i).getCertificatedDate()));
					ps2.setString(5, cd.getCandidateID());
					ps2.execute();
				}
			}
			if(cd instanceof Experience) {
				String sql2 = "INSERT INTO Experience VALUES(?, ?, ?)";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, cd.getCandidateID());
				ps2.setInt(2, ((Experience) cd).getExpInYear());
				ps2.setString(3, ((Experience) cd).getProSkill());
				ps2.execute();
			}
			if(cd instanceof Fresher) {
				String sql2 = "INSERT INTO Fresher VALUES(?, ?, ?, ?)";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, cd.getCandidateID());
				ps2.setDate(2, convertToSQLDate(((Fresher) cd).getGraduation_Date()));
				ps2.setString(3, ((Fresher) cd).getGraduation_Rank());
				ps2.setString(4, ((Fresher) cd).getEducation());
				ps2.execute();
			}
			if(cd instanceof Intern) {
				String sql2 = "INSERT INTO Intern VALUES(?, ?, ?, ?)";
				PreparedStatement ps2 = conn.prepareStatement(sql2);
				ps2.setString(1, cd.getCandidateID());
				ps2.setString(2, ((Intern) cd).getMajors());
				ps2.setInt(3, ((Intern) cd).getSemester());
				ps2.setString(4, ((Intern) cd).getUniversity_name());
				ps2.execute();
			}
		}
		catch(SQLException i) {
			Candidate.logError.error(i);
		}
		catch(Exception i) {
			Candidate.logError.error(i);
		}
		finally {
			conn.close();
		}
		Candidate.candidate_Count++;
	}

	public static String idCDNew() throws SQLException {
		String cdIDNew = null;
		Connection conn = null;
		try {
			conn = ConnectionDB.getMyConnect();
			PreparedStatement preparedStatement = null;
			String sql = "SELECT CandidateID FROM Candidate";
			preparedStatement = conn.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			int max = 0;
			while (rs.next()) {
				String id = rs.getString("CandidateID");
				String id2 = id.substring(2, 5);
				int x = Integer.parseInt(id2);
				if(x>max) {
					max=x;
				}
			}
			max+=1;
			String idString = ""+max;
			if(idString.length()==3) {
				cdIDNew = "CD"+idString;
			}
			if(idString.length()==2) {
				cdIDNew = "CD0"+idString;
			}
			if(idString.length()==1) {
				cdIDNew = "CD00"+idString;
			}
		}
		catch(SQLException i) {
			Candidate.logError.error(i);
		}
		catch(Exception i) {
			Candidate.logError.error(i);
		}
		finally {
			conn.close();
		}
		return cdIDNew;
	}

	public static ArrayList<Candidate> getListCandidateFromDB() throws SQLException {
		ArrayList<Candidate> candidateList = new ArrayList<Candidate>();
		Connection conn = null;
		try {	
			conn = ConnectionDB.getMyConnect();
			PreparedStatement preparedStatement = null;
			String sql = " SELECT C.CandidateID, CandidateType, FullName, Birthday, Phone, Email, "+
					"GraduationDate, GraduationRank, Education, Majors, Semester, University_name, ExpInYear, Proskill "+
					"FROM Candidate C LEFT JOIN Fresher F ON C.CandidateID = F.CandidateID "+
					"LEFT JOIN Intern I ON C.CandidateID = I.CandidateID "+
					"LEFT JOIN Experience E ON C.CandidateID = E.CandidateID ";
			logInfo.info(sql);
			preparedStatement = conn.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int type = 	rs.getInt("CandidateType");
				String cdID = rs.getString("CandidateID");
				String name = rs.getString("FullName");
				Date birthday = rs.getDate("Birthday");
				String phone = rs.getString("Phone");
				String email = rs.getString("Email");

				String sqlCE = "SELECT * FROM Certificated WHERE CandidateID = ?";
				logInfo.info(sqlCE);
				preparedStatement = conn.prepareStatement(sqlCE);
				preparedStatement.setString(1, cdID);
				ResultSet rs2 = preparedStatement.executeQuery();
				ArrayList<Certificated> ce_List = new ArrayList<Certificated>();
				while(rs2.next()) {
					String certificatedID = rs2.getString("CertificatedID");
					String certificateName = rs2.getString("CertificatedName");
					String certificateRank = rs2.getString("CertificatedRank");
					Date certificateDate = rs2.getDate("CertificatedDate");
					Certificated certificated = new Certificated(certificatedID, certificateName, certificateRank, certificateDate);
					ce_List.add(certificated);
				}
				if( type == 0) {
					Experience exp = new Experience();
					exp.setCandidateID(cdID);
					exp.setFullName(name);
					exp.setBirthDay(birthday);
					exp.setPhone(phone);
					exp.setEmail(email);
					exp.setList_Certificated(ce_List);
					exp.setExpInYear(rs.getInt("ExpInYear"));
					exp.setProSkill(rs.getString("Proskill"));
					candidateList.add(exp);
				}
				if( type == 1) {
					Fresher fs = new Fresher();
					fs.setCandidateID(cdID);
					fs.setFullName(name);
					fs.setBirthDay(birthday);
					fs.setPhone(phone);
					fs.setEmail(email);
					fs.setList_Certificated(ce_List);
					fs.setGraduation_Date(rs.getDate("GraduationDate"));
					fs.setGraduation_Rank(rs.getString("GraduationRank"));
					fs.setEducation(rs.getString("Education"));
					candidateList.add(fs);
				}
				if( type == 2) {
					Intern it = new Intern();
					it.setCandidateID(cdID);
					it.setFullName(name);
					it.setBirthDay(birthday);
					it.setPhone(phone);
					it.setEmail(email);
					it.setList_Certificated(ce_List);
					it.setMajors(rs.getString("Majors"));
					it.setSemester(rs.getInt("Semester"));
					it.setUniversity_name(rs.getString("University_name"));
					candidateList.add(it);
				}
			}
		}
		catch(SQLException i) {
			Candidate.logError.error(i);
		}
		catch(Exception i) {
			Candidate.logError.error(i);
		}
		finally{
			conn.close();
		}

		return candidateList;
	}

	public static void sortCandidate(ArrayList<Candidate> candidateList) {
		Collections.sort(candidateList);
		for(Candidate i: candidateList) {
			i.showMe();
		}
	}

	public static void getNameAllCandidate(ArrayList<Candidate> candidateList) {
		StringBuffer sb = new StringBuffer();
		for(Candidate candidate: candidateList) {
			sb.append(candidate.getFullName());
			sb.append(", ");
		}
		sb.replace(sb.length()-2, sb.length()-1, ".");
		System.out.println(sb);
	}

	public static void insertCadidateToDBNoSql(Candidate cd) throws SQLException{
		Connection conn = null;
		try {
			conn = ConnectionDB.getMyConnect();
			String sql = "Select * FROM Candidate";
			logInfo.info(sql);
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = ps.executeQuery();
			conn.setAutoCommit(false);
			rs.moveToInsertRow(); // nhay sang 1 dong trong de them
			rs.updateString("CandidateID",cd.getCandidateID()); 
			rs.updateInt("CandidateType",cd.getCandidate_Type()); 
			rs.updateString("FullName",cd.getFullName()); 
			rs.updateDate("Birthday", convertToSQLDate(cd.getBirthDay()));
			rs.updateString("Phone",cd.getPhone());
			rs.updateString("Email",cd.getEmail());
			rs.insertRow(); // them bo du lieu do vao CSDL
			if(cd.getList_Certificated()!=null){
				String sql2 = "Select * FROM Certificated";
				logInfo.info(sql2);
				PreparedStatement ps2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs2 = ps2.executeQuery();
				for(int i=0; i<cd.getList_Certificated().size(); i++){
					rs2.moveToInsertRow(); // nhay sang 1 dong trong de them
					rs2.updateString("CertificatedID",cd.getList_Certificated().get(i).getCertificatedID()); 
					rs2.updateString("CertificatedName",cd.getList_Certificated().get(i).getCertificateName()); 
					rs2.updateString("CertificatedRank",cd.getList_Certificated().get(i).getCertificateRank()); 
					rs2.updateDate("CertificatedDate", convertToSQLDate(cd.getList_Certificated().get(i).getCertificatedDate()));
					rs2.updateString("CandidateID",cd.getCandidateID());
					rs2.insertRow(); // them bo du lieu do vao CSDL
				}
			}
			if(cd instanceof Experience){
				String sql3 = "Select * FROM Experience";
				logInfo.info(sql3);
				PreparedStatement ps3 = conn.prepareStatement(sql3, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs3 = ps3.executeQuery();
				rs3.moveToInsertRow(); // nhay sang 1 dong trong de them
				rs3.updateString("CandidateID",cd.getCandidateID()); 
				rs3.updateInt("ExpInYear",((Experience) cd).getExpInYear()); 
				rs3.updateString("ProSkill",((Experience) cd).getProSkill()); 
				rs3.insertRow(); // them bo du lieu do vao CSDL	
			}
			if(cd instanceof Fresher){
				String sql3 = "Select * FROM Fresher";
				logInfo.info(sql3);
				PreparedStatement ps3 = conn.prepareStatement(sql3, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs3 = ps3.executeQuery();
				rs3.moveToInsertRow(); // nhay sang 1 dong trong de them
				rs3.updateString("CandidateID",cd.getCandidateID()); 
				rs3.updateDate("GraduationDate", convertToSQLDate(((Fresher) cd).getGraduation_Date()));
				rs3.updateString("GraduationRank",((Fresher) cd).getGraduation_Rank()); 
				rs3.updateString("Education",((Fresher) cd).getEducation()); 
				rs3.insertRow(); // them bo du lieu do vao CSDL	
			}
			if(cd instanceof Intern){
				String sql3 = "Select * FROM Intern";
				logInfo.info(sql3);
				PreparedStatement ps3 = conn.prepareStatement(sql3, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs3 = ps3.executeQuery();
				rs3.moveToInsertRow(); // nhay sang 1 dong trong de them
				rs3.updateString("CandidateID",cd.getCandidateID()); 
				rs3.updateString("Majors",((Intern) cd).getMajors());
				rs3.updateInt("Semester",((Intern) cd).getSemester()); 
				rs3.updateString("University_name",((Intern) cd).getUniversity_name()); 
				rs3.insertRow(); // them bo du lieu do vao CSDL	
			}
			conn.commit();
		}
		catch(SQLException i){
			Candidate.logError.error(i);
		}
		catch(Exception i){
			Candidate.logError.error(i);
		}
		finally {
			conn.close();
		}
		Candidate.candidate_Count++;
	}

	public static boolean updateGeneralInfo(String cdID) throws SQLException {
		Connection conn =  null;
		boolean check = false;
		Scanner sc = new Scanner(System.in);
		try {
			conn = ConnectionDB.getMyConnect();
			String sql = "Select * FROM Candidate WHERE CandidateID = ?";
			logInfo.info(sql);
			PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ps.setString(1, cdID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				check = true;
				System.out.print("---> Cap nhat FullName moi: ");
				String name = sc.nextLine();
				rs.updateString("FullName", name);
				
				System.out.print("---> Cap nhat BirthDay moi: ");
				java.util.Date birthday = CandidateValiDate.enterBirthday();
				rs.updateDate("Birthday", convertToSQLDate(birthday));
				
				System.out.print("---> Cap nhat Phone moi: ");
				String phone = CandidateValiDate.enterPhone();
				rs.updateString("Phone", phone);
				
				System.out.print("---> Cap nhat Email moi: ");
				String email = CandidateValiDate.checkEmail();
				rs.updateString("Email",email);
				rs.updateRow();
			}

			if(check == false) {
				System.out.println("Candidate co CandidateID "+cdID+" khong ton tai trong DB");
			} 
			else 
			{
				String sql2 = "Select * FROM Certificated WHERE CandidateID = ?";
				logInfo.info(sql2);
				PreparedStatement ps2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ps2.setString(1, cdID);
				ResultSet rs2 = ps2.executeQuery();
				int t =1;
				while(rs2.next()){
					System.out.println("---> Cap nhat thong tin bang cap thu "+t);

					System.out.print("---> Cap nhat CertificatedID moi: ");
					String CertificatedID = sc.nextLine();
					rs2.updateString("CertificatedID",CertificatedID);

					System.out.print("---> Cap nhat CertificateName moi: ");
					String CertificateName = sc.nextLine();
					rs2.updateString("CertificatedName",CertificateName);

					System.out.print("---> Cap nhat CertificateRank moi: ");
					String CertificateRank = sc.nextLine();
					rs2.updateString("CertificatedRank",CertificateRank);

					System.out.print("---> Cap nhat CertificatedDate moi: ");
					java.util.Date CertificatedDate = CandidateValiDate.enterDate("CertificatedDate");
					rs2.updateDate("CertificatedDate",convertToSQLDate(CertificatedDate));
					t++;
					rs2.updateRow();
				}
			}
		}
		catch(SQLException i) {
			Candidate.logError.error(i);
		}
		catch(Exception i) {
			Candidate.logError.error(i);
		}
		finally {
			conn.close();
		}
		return check;
	}

	public static void updateInfoNoSql() throws SQLException {
		Connection conn = null;
		Scanner sc = new Scanner(System.in);
		try {
			conn = ConnectionDB.getMyConnect();
			System.out.print("Nhap CandidateID muon Update: ");
			String cdID = sc.nextLine();
			boolean check = updateGeneralInfo(cdID);
			if(check) {
				String sqlUD = "Select CandidateType FROM Candidate WHERE CandidateID = ?";
				logInfo.info(sqlUD);
				PreparedStatement psUD = conn.prepareStatement(sqlUD, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				psUD.setString(1, cdID);
				ResultSet resultSet = psUD.executeQuery();
				while(resultSet.next()){
					if(resultSet.getInt("CandidateType") == 0){
						String sql2 = "Select * FROM Experience WHERE CandidateID = ?";
						logInfo.info(sql2);
						PreparedStatement ps2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						ps2.setString(1, cdID);
						ResultSet rs2 = ps2.executeQuery();
						while(rs2.next()){
							System.out.print("---> Cap nhat ExpInYear moi: ");
							int ExpInYear = CandidateValiDate.enterInt();
							rs2.updateInt("ExpInYear", ExpInYear);
							sc.nextLine();
							System.out.print("---> Cap nhat ProSkill moi: ");
							String ProSkill = sc.nextLine();
							rs2.updateString("ProSkill", ProSkill);
							rs2.updateRow();
						}
					}

					if(resultSet.getInt("CandidateType") == 1){
						String sql2 = "Select * FROM Fresher WHERE CandidateID = ?";
						logInfo.info(sql2);
						PreparedStatement ps2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						ps2.setString(1, cdID);
						ResultSet rs2 = ps2.executeQuery();
						while(rs2.next()){
							System.out.print("---> Cap nhat Graduation_date moi: ");
							java.util.Date Graduation_date = CandidateValiDate.enterDate("Graduation_date");
							rs2.updateDate("GraduationDate", convertToSQLDate(Graduation_date));
							System.out.print("---> Cap nhat Graduation_rank moi: ");
							String Graduation_Rank = sc.nextLine();
							rs2.updateString("GraduationRank", Graduation_Rank);
							System.out.print("---> Cap nhat Education moi: ");
							String Education = sc.nextLine();
							rs2.updateString("Education", Education);
							rs2.updateRow();
						}
					}
					if(resultSet.getInt("CandidateType") == 2){
						String sql2 = "Select * FROM Experience WHERE CandidateID = ?";
						logInfo.info(sql2);
						PreparedStatement ps2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						ps2.setString(1, cdID);
						ResultSet rs2 = ps2.executeQuery();
						while(rs2.next()){
							System.out.print("---> Cap nhat Majors moi: ");
							String majors = sc.nextLine();
							rs2.updateString("Majors", majors);
							System.out.print("---> Cap nhat Semester moi: ");
							int semester = CandidateValiDate.enterInt();
							rs2.updateInt("Semester", semester);
							sc.nextLine();
							System.out.print("---> Cap nhat University_name moi: ");
							String university_name = sc.nextLine();
							rs2.updateString("University_name", university_name);
							rs2.updateRow();
						}
					}
				}
			}
		}
		catch(SQLException i) {
			Candidate.logError.error(i);
		}
		catch(Exception i) {
			Candidate.logError.error(i);
		}
		finally {
			conn.close();
		}
	}
}
