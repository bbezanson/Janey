package com.janey.handlers.get;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseHandler;
import com.janey.handlers.Handler;

public class GetConfigHandler extends BaseHandler implements Handler {

	public GetConfigHandler() {
		super(Actions.GET_CONFIG);
		// TODO Auto-generated constructor stub
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		Properties props = daoManager.getProperties();
		
		out.object();
		out.array();
		Set<Object> keys = props.keySet();
		for ( Object key : keys ) {
			String k = (String)key;
			String v = props.getProperty(k);
			out.object();
			out.key("key");out.value(k);
			out.key("val");out.value(v);
			out.endObject();
		}
		out.endArray();
		out.endObject();
	}

}
