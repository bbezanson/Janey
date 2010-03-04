package com.janey.handlers;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.core.dao.DAOManager;

public interface Handler {
	public abstract void handle(JaneySession js, DAOManager daoManager, JSONObject json, JSONWriter out) 
		throws SQLException, JSONException, JaneyException;
}
