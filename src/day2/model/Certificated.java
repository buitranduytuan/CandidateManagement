/**
 *@author TuanBTD
 *@date Dec 27, 2019
 *@version version
 */

package day2.model;

import java.util.Date;

public class Certificated {
	private String certificatedID;
	private String certificateName;
	private String certificateRank;
	private Date certificatedDate;

	public Certificated() {
	}
		
	public Certificated(String certificatedID, String certificateName, String certificateRank, Date certificatedDate) {
		this.certificatedID = certificatedID;
		this.certificateName = certificateName;
		this.certificateRank = certificateRank;
		this.certificatedDate = certificatedDate;
	}

	public String getCertificatedID() {
		return certificatedID;
	}

	public void setCertificatedID(String certificatedID) {
		this.certificatedID = certificatedID;
	}

	public String getCertificateName() {
		return certificateName;
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName;
	}

	public String getCertificateRank() {
		return this.certificateRank;
	}

	public void setCertificateRank(String certificateRank) {
		this.certificateRank = certificateRank;
	}

	public Date getCertificatedDate() {
		return this.certificatedDate;
	}

	public void setCertificatedDate(Date certificatedDate) {
		this.certificatedDate = certificatedDate;
	}
}
