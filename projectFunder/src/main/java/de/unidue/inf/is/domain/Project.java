package de.unidue.inf.is.domain;

import java.util.ArrayList;
import java.util.List;

public class Project {

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public double getFund_limit() {
		return fund_limit;
	}
	public void setFund_limit(double fund_limit) {
		this.fund_limit = fund_limit;
	}
	public double getActual_fund() {
		return actual_fund;
	}
	public void setActual_fund(double actual_fund) {
		this.actual_fund = actual_fund;
	}
	public User getCreated_by() {
		return created_by;
	}
	public void setCreated_by(User created_by) {
		this.created_by = created_by;
	}
	public Project getVorganger() {
		return vorganger;
	}
	public void setVorganger(Project vorganger) {
		this.vorganger = vorganger;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public void addActualFund(double donation) {
		this.actual_fund += donation;
	}

	

	public Project() {
		// TODO Auto-generated constructor stub
	}

	private String id;
	private String name;
	private Category category;
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Spenden> getSpendens() {
		return spendens;
	}
	public void setSpendens(List<Spenden> spendens) {
		this.spendens = spendens;
	}

	private double fund_limit;
	private double actual_fund;
	private User created_by;

	private Project vorganger;
	private String description;

	private List<Comment> comments;
	private List<Spenden> spendens;

}