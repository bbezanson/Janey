package com.janey.core.objects;

import java.sql.Timestamp;
import java.text.DateFormat;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

public class Comment {
	private long id;
	private long issueId;
	private int type;
	private Timestamp date;
	private String comment;
	
	public Comment() {
	}
	
	public Comment(JSONObject json) throws JSONException {
		this.id = json.has("id") ? json.getLong("id") : -1;
		this.issueId = json.getLong("issue_id");
		this.type = json.getInt("type");
//		this.date = new Timestamp(json.getLong("date"));
		this.comment = json.getString("comment");
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIssueId() {
		return issueId;
	}
	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public void toJson(JSONWriter out) throws JSONException {
		DateFormat df = DateFormat.getDateInstance();
		out.object();
		out.key("id");out.value(this.id);
		out.key("issue_id");out.value(this.issueId);
		out.key("type");out.value(this.type);
		out.key("date");out.value(df.format(this.date));
		out.key("comment");out.value(this.comment);
		out.endObject();
	}
}
