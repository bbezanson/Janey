package com.janey.handlers.create;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseHandler;
import com.janey.handlers.Handler;

public class CreateConfigHandler extends BaseHandler implements Handler {

	public CreateConfigHandler() {
		super(Actions.CREATE_CONFIG);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		if ( daoManager.getProperties().isEmpty() ) {
			Iterator<String> iter = json.keys();
			Properties props = new Properties();
		
			while ( iter.hasNext() ) {
				String key = iter.next();
				if ( key.startsWith("com.janey") ) {
					props.setProperty(key, json.getString(key));
				}
			}
		
			daoManager.getPrefsManager().save(props);
			returnStatus(out, STAT_SUCCESS);
		} else {
			returnStatus(out, ERROR_CONFIG);
		}
	}

}
