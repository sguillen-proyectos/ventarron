package org.inf325.ventarron.dao;

import java.sql.SQLException;

import org.inf325.ventarron.utils.HashUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DbHelper extends OrmLiteSqliteOpenHelper {
	/* Database constants */
	private static final String DATABASE_NAME = "ventarron_db";
	private static final int DATABASE_VERSION = 1;

	/* Logging constants */
	private final String LOG_TAG = getClass().getName();

	/* Database objects */
	private RuntimeExceptionDao<Depot, Integer> depotDao;
	private RuntimeExceptionDao<Product, Integer> productDao;
	private RuntimeExceptionDao<User, Integer> userDao;
	private RuntimeExceptionDao<Client, Integer> clientDao;

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		createTables(connectionSource);
		createInitialData();
	}

	/**
	 * Create tables for database
	 * 
	 * @param connectionSource
	 */
	private void createTables(ConnectionSource connectionSource) {
		try {
			Log.i(LOG_TAG, "Creating database tables");
			// TODO: When new relations exists, create in a specific order
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, Depot.class);
			TableUtils.createTable(connectionSource, Product.class);
			TableUtils.createTable(connectionSource, Client.class);
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Can\'t create tables.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create initial data in "depot" table. As original problem says that there
	 * is only three depots.
	 */
	private void createInitialData() {
		createDepotInitialData();
		createUserInitialData();
	}

	private void createUserInitialData() {
		// Seller user
		User user100 = new User();
		user100.setUsername("vendedor1");
		user100.setPassword(HashUtil.calculateMD5("12345"));
		user100.setRole(User.ROLE_SELLER);
		// Depot user
		User user200 = new User();
		user200.setUsername("almacen1");
		user200.setPassword(HashUtil.calculateMD5("12345"));
		user200.setRole(User.ROLE_DEPOT);
		
		RuntimeExceptionDao<User,Integer> userDao2 = getUserDao();
		
		userDao2.create(user100);
		userDao2.create(user200);
	}

	private void createDepotInitialData() {
		Depot depotA = new Depot("Almacen A");
		Depot depotB = new Depot("Almacen B");
		Depot depotC = new Depot("Almacen C");

		RuntimeExceptionDao<Depot, Integer> depotDao = getDepotDao();

		depotDao.create(depotA);
		depotDao.create(depotB);
		depotDao.create(depotC);

		Log.i(LOG_TAG, "Created initial information. (Depots)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {

		dropTables(database, connectionSource);
	}

	private void dropTables(SQLiteDatabase database,
			ConnectionSource connectionSource) {

		try {
			TableUtils.dropTable(connectionSource, Client.class, true);
			TableUtils.dropTable(connectionSource, Product.class, true);
			TableUtils.dropTable(connectionSource, Depot.class, true);
			TableUtils.dropTable(connectionSource, User.class, true);
			Log.i(LOG_TAG, "Database tables dropped successfully");

			onCreate(database, connectionSource);
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Error on removing database tables.", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns the Data Access Object for Client class
	 * @return
	 */
	public RuntimeExceptionDao<Client, Integer> getClientDao() {
		if (clientDao == null) {
			clientDao = getRuntimeExceptionDao(Client.class);
		}
		return clientDao;
	}
	
	/**
	 * Returns the Data Access Object for Depot class
	 * @return
	 */
	public RuntimeExceptionDao<User, Integer> getUserDao() {
		if (userDao == null) {
			userDao = getRuntimeExceptionDao(User.class);
		}
		return userDao;
	}

	/**
	 * Returns the Data Access Object for Depot class
	 * @return
	 */
	public RuntimeExceptionDao<Depot, Integer> getDepotDao() {
		if (depotDao == null) {
			depotDao = getRuntimeExceptionDao(Depot.class);
		}
		return depotDao;
	}

	/**
	 * Return the Data Access Object for Product class
	 * @return
	 */
	public RuntimeExceptionDao<Product, Integer> getProductDao() {
		if (productDao == null) {
			productDao = getRuntimeExceptionDao(Product.class);
		}
		return productDao;
	}

}
