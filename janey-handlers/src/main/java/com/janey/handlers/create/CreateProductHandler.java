package com.janey.handlers.create;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import com.janey.core.dao.DAOManager;
import com.janey.core.helpers.JaneyException;
import com.janey.core.helpers.JaneySession;
import com.janey.core.objects.Product;
import com.janey.handlers.Actions;
import com.janey.handlers.BaseHandler;
import com.janey.handlers.Handler;

public class CreateProductHandler extends BaseHandler implements Handler {

	public CreateProductHandler() {
		super(Actions.CREATE_PRODUCT);
		// TODO Auto-generated constructor stub
	}

	public void handle(JaneySession js, DAOManager daoManager, JSONObject json,
			JSONWriter out) throws SQLException, JSONException, JaneyException {
		// TODO Auto-generated method stub
		Product product = new Product(json);
		daoManager.getProductsManager().create(product);
	}

}
