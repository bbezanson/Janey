package com.janey.core.objects;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

public class Issue {
	private long id;
	private long productId;
	private int status;
	private int type;
	private int severity;
	private int platform;
	private String title;
	private String description;
	private String reportedBy;
	private Timestamp reportedDate;
	private String reportedVersion;
	private String assignedTo;
	private String resolvedBy;
	private Timestamp resolvedDate;
	private String resolvedVersion;
	
	public Issue() {
	}
	public Issue(JSONObject json) throws JSONException {
		this.id = json.has("id") ? json.getLong("id") : -1;
		this.productId = json.getLong("product_id");
		this.status = json.getInt("status");
		this.type = json.getInt("type");
		this.severity = json.getInt("severity");
		this.platform = json.getInt("platform");
		this.title = json.getString("title");
		this.description = json.getString("description");
		this.reportedBy = json.getString("reported_by");
//		this.reportedDate = new Timestamp(json.getLong("reported_date"));
		this.reportedVersion = json.getString("reported_version");
		this.assignedTo = json.getString("assigned_to");
		this.resolvedBy = json.has("resolved_by") ? json.getString("resolved_by") : "";
//		this.resolvedDate = new Timestamp(json.getLong("resolved_date"));
		this.resolvedVersion = json.has("resolved_version") ? json.getString("resolved_version") : "";
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
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
	public String getReportedBy() {
		return reportedBy;
	}
	public void setReportedBy(String reportedBy) {
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
	public String getResolvedBy() {
		return resolvedBy;
	}
	public void setResolvedBy(String resolvedBy) {
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
	public void toJson(JSONWriter out) throws JSONException {
		out.object();
		out.key("id");out.value(this.id);
		out.key("product_id");out.value(this.productId);
		out.key("status");out.value(this.status);
		out.key("type");out.value(this.type);
		out.key("severity");out.value(this.severity);
		out.key("platform");out.value(this.platform);
		out.key("title");out.value(this.title);
		out.key("description");out.value(this.description);
		out.key("reported_by");out.value(this.reportedBy);
		out.key("reported_date");out.value(this.reportedDate);
		out.key("reported_version");out.value(this.reportedVersion);
		out.key("resolved_by");out.value(this.reportedBy);
		out.key("resolved_date");out.value(this.reportedDate);
		out.key("resolved_version");out.value(this.reportedVersion);
		out.endObject();
	}
}
