package com.janey.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.dao.DAOManagerPool;
import com.janey.core.helpers.EmailHelper;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.core.managers.PrefsManager;
import com.janey.core.managers.impl.PrefsManagerImpl;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseHandler;
import com.janey.handlers.HandlerManager;

public class JsonServlet extends HttpServlet {
	private static final long serialVersionUID = -1961059737332485864L;
	final private static Logger log = Logger.getLogger(JsonServlet.class);

	private DAOManagerPool daoManagerPool = null;
	private HandlerManager handlerManager;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.debug("initializing the janey core servlet");
		
		try {
			log.debug("initializing the preferences");
			PrefsManager prefsManager = new PrefsManagerImpl();
			Properties props = prefsManager.restore();
			
			log.debug("initializing the dao manager pool");
			this.daoManagerPool = new DAOManagerPool(props);
		} catch (SQLException e) {
			log.error("SQLException while creating dao manager pool:" +e.getMessage(), e);
		}
		log.debug("initializing the handler manager");
		this.handlerManager = new HandlerManager();
	}

	@Override
	public void destroy() {
		log.debug("destroying the janey core servet");
		
		try {
			log.debug("destroying the database connection pool");
			this.daoManagerPool.destroy();
		} catch (SQLException e) {
			log.error("SQLException while destroying dao manager pool:" +e.getMessage(), e);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("DELETE request received");
		// handle the request
		doHandle(req, resp);
		//super.doDelete(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("GET request received");
		// handle the request
		doHandle(req, resp);
		//super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("POST request received");
		// handle the request
		doHandle(req, resp);
		//super.doPost(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.debug("PUT request received");
		// handle the request
		doHandle(req, resp);
		super.doPut(req, resp);
	}
	
	private boolean doHandle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.debug("==> doHandle");
		boolean success = false;
		
		// build all the objects that the handlers may/may not require 
		JSONObject json = getJSON(req);
		if ( json != null ) {
			log.debug("JSON request recieved:" + json.toString());
			// properly set the response type to json
			resp.setContentType("text/json-comment-filtered");
			JaneySession js = new JaneySession(req, resp);
			DAOManager daoManager = this.daoManagerPool.next();
			
			JSONWriter out = new JSONWriter(resp.getWriter());
			try {
				// retrieve the action from the JSON
				String action = json.getString("action");
				
				// find the proper handler for the action
				log.debug("doHandle: searching for hander for action:" + action );
				BaseHandler handler = this.handlerManager.findHandler(action);
				
				// handle the action using the handler
				if ( handler != null ) {
					log.debug("handler found! calling...");
					// TODO: handlers should return something to indicate success
					try {
						handler.handle(js, daoManager, json, out);
						// if this is a create config then we need to init the managers
						if ( handler.getAction().equals(Actions.CREATE_CONFIG)) {
							Properties props = daoManager.getPrefsManager().restore();
							List<DAOManager> managers = this.daoManagerPool.getAll();
							for ( DAOManager manager : managers ) {
								manager.init(props);
							}
						}
					} catch (JaneyException e) {
						log.debug("JaneyException in handler:" + e.getMessage());
						out.object();
						out.key("message");
						out.value(e.getMessage());
						out.endObject();
					}
				} else {
					out.object();
					out.key("message");
					out.value("Invalid Handler Action:" + action);
					out.endObject();
				}
				
				// commit any pending changes to the database
				daoManager.sync();
				success = true;
			} catch (JSONException e) {
				log.error("JSONException in doHandle:" + e.getMessage(), e);
				String subject = "Janey Exception:" + e.getMessage();
				StringBuilder body = new StringBuilder();
				StackTraceElement[] ste = e.getStackTrace();
				for ( StackTraceElement st : ste ) {
					body.append(st.toString() + "\n");
				}
				body.append(json);
				
				EmailHelper emailHelper = new EmailHelper(daoManager.getProperties());
				try {
					emailHelper.sendmail("wade@tikiwade.com", subject, body.toString());
				} catch (Throwable e2) {
					log.error("Unable to email error message:" + e2.getMessage(), e2);
				}
				try {
					// rollback any pending changes in the database
					daoManager.reset();
				} catch (SQLException e1) {
					log.error("ERROR resetting database:" + e.getMessage(), e);
				}
				
			} catch (SQLException e) {
				log.error("SQLException in doHandle:" + e.getMessage(), e);
				String subject = "Janey Exception:" + e.getMessage();
				StringBuilder body = new StringBuilder();
				StackTraceElement[] ste = e.getStackTrace();
				for ( StackTraceElement st : ste ) {
					body.append(st.toString() + "\n");
				}
				body.append(json);
				
				EmailHelper emailHelper = new EmailHelper(daoManager.getProperties());
				try {
					emailHelper.sendmail("wade@tikiwade.com", subject, body.toString());
				} catch (Throwable e2) {
					log.error("Unable to email error message:" + e2.getMessage(), e2);
				}
			}
			
			// report an error back to the user if there was no success
			if ( !success ) {
				try {
					out.object();
					out.key("message");
					out.value("WooHoo! You just found an error in janey. <br>We will start working to correct the problem, Please try again later.");
					out.endObject();
				} catch (JSONException e) {
					log.error("JSONException while reporting error:" + e.getMessage(), e);
				}
			}
		} else {
			log.debug("no JSON in the request, assuming this is a ping");
			resp.setContentType("text/html");
		
			String val = req.getParameter("ping");
			log.debug("returning ping reponse:" + val);
			
			PrintWriter out = resp.getWriter();
			out.print(val);
			out.flush();
		}
		log.debug("<== doHandle");
		return success;
	}
		
	/**
	 * construct a JSON object from the incoming request
	 * @param req
	 * @return JSONObject
	 * @throws IOException
	 */
	public JSONObject getJSON(HttpServletRequest req) throws IOException {
		JSONObject json = null;
		
		// get the content
		int contentLength = req.getContentLength();
		String contentType = req.getContentType();
		byte[] data = new byte[contentLength];
		req.getInputStream().read(data);
		// decode the content
		String enc = req.getCharacterEncoding();
		if ( enc == null ) {
			enc = "UTF-8";
		}
		String content = URLDecoder.decode(new String(data), enc);

		log.debug("content type:" + contentType + " content length:" + contentLength + " content:" + content);

		if ( content.startsWith("json=") ) {
			try {
				json = new JSONObject(content.substring(5));
			} catch (JSONException e) {
				log.error("Error creating JSON obj:" + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return json;
	}
}
