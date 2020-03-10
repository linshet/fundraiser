package de.unidue.inf.is.domain;

public class Spenden {
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProjectid() {
		return projectid;
	}
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	public String getSichtbarkeit() {
		return sichtbarkeit;
	}
	public void setSichtbarkeit(String sichtbarkeit) {
		this.sichtbarkeit = sichtbarkeit;
	}
	public Double getSpendenbetrag() {
		return spendenbetrag;
	}
	public void setSpendenbetrag(Double spendenbetrag) {
		this.spendenbetrag = spendenbetrag;
	}
	private String projectid;
	private String sichtbarkeit;
	private Double spendenbetrag;

}
