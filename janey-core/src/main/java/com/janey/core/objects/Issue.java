package com.janey.core.objects;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;

public class Issue {
	private long id;
	private long productId;
	private int status;
	private int type;
	private int severity;
	private int platform;
	private String title;
	private String description;
	private long reportedBy;
	private Timestamp reportedDate;
	private String reportedVersion;
	private long assignedTo;
	private long resolvedBy;
	private Timestamp resolvedDate;
	private String resolvedVersion;
	
	public Issue() {
	}
	public Issue(JSONObject json) throws JSONException {
		this.id = json.getLong("id");
		this.productId = json.getLong("product_id");
		this.status = json.getInt("status");
		this.type = json.getInt("type");
		this.severity = json.getInt("severity");
		this.platform = json.getInt("platform");
		this.title = json.getString("title");
		this.description = json.getString("description");
		this.reportedBy = json.getLong("reported_by");
		//this.reportedDate = json.getLong("reported_date");
		this.reportedVersion = json.getString("reported_version");
		this.resolvedBy = json.getLong("resolved_by");
		//this.resolvedDate = json.getLong("resolved_date");
		this.resolvedVersion = json.getString("resolved_version");
	}
	public long getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(long assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public long getReportedBy() {
		return reportedBy;
	}
	public void setReportedBy(long reportedBy) {
		this.reportedBy = reportedBy;
	}
	public Timestamp getReportedDate() {
		return reportedDate;
	}
	public void setReportedDate(Timestamp reportedDate) {
		this.reportedDate = reportedDate;
	}
	public String getReportedVersion() {
		return reportedVersion;
	}
	public void setReportedVersion(String reportedVersion) {
		this.reportedVersion = reportedVersion;
	}
	public long getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(long resolvedBy) {
		this.resolvedBy = resolvedBy;
	}
	public Timestamp getResolvedDate() {
		return resolvedDate;
	}
	public void setResolvedDate(Timestamp resolvedDate) {
		this.resolvedDate = resolvedDate;
	}
	public String getResolvedVersion() {
		return resolvedVersion;
	}
	public void setResolvedVersion(String resolvedVersion) {
		this.resolvedVersion = resolvedVersion;
	}
	public int getSeverity() {
		return severity;
	}
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

}
