/**
*@author TuanBTD
*@date Dec 30, 2019
*@version version
*/

package day2.main;


import java.sql.SQLException;
import day2.model.QuanLyCandidate;

public class MainCandidate {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	
	public static void main(String[] args) throws SQLException {
		QuanLyCandidate ql = new QuanLyCandidate();
		ql.menu();
	}
}
