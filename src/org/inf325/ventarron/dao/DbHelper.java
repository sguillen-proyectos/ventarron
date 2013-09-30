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
	private RuntimeExceptionDao<UserRole, Integer> userRoleDao;
	private RuntimeExceptionDao<Role, String> roleDao;

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
			TableUtils.createTable(connectionSource, Role.class);
			TableUtils.createTable(connectionSource, UserRole.class);
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
		createRoleInitialData();
		createUserInitialData();
		createUserRoleInitialData();
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
	
	private void createRoleInitialData() {
		Role productsRole = new Role();
		productsRole .setName(Role.PRODUCTS_ROLE);
		Role clientsRole = new Role();
		clientsRole.setName(Role.CLIENTS_ROLE);
		Role usersRole = new Role();
		usersRole.setName(Role.USERS_ROLE);
		
		RuntimeExceptionDao<Role, String> roleDao = getRoleDao();
		
		roleDao.create(productsRole);
		roleDao.create(clientsRole);
		roleDao.create(usersRole);
		
		Log.i(LOG_TAG, "Created initial information. (Roles)");
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
			TableUtils.dropTable(connectionSource, Role.class, true);
			TableUtils.dropTable(connectionSource, UserRole.class, true);
			
			Log.i(LOG_TAG, "Database tables dropped successfully");

			onCreate(database, connectionSource);
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Error on removing database tables.", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns the Data Access Object for the Role class
	 * @return
	 */
	public RuntimeExceptionDao<Role, String> getRoleDao() {
		if (roleDao == null) {
			roleDao = getRuntimeExceptionDao(Role.class);
		}
		return roleDao;
	}
	
	/**
	 * Returns the Data Access Object for the UserRole class
	 * @return
	 */
	public RuntimeExceptionDao<UserRole, Integer> getUserRoleDao() {
		if (userRoleDao == null) {
			userRoleDao = getRuntimeExceptionDao(UserRole.class);
		}
		return userRoleDao;
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
	
	private void createUserInitialData() {
		RuntimeExceptionDao<User,Integer> userDao2 = getUserDao();
		User user;
		
		for (int i = 1; i <= 6; ++i) {
			user = new User();
			user.setName("Usuario " + i);
			user.setUsername("user" + i);
			user.setPassword(HashUtil.calculateMD5("12345"));
			user.setRoot(false);
			
			userDao2.create(user);
		}

		// Root user, can only handle users
		user = new User();
		user.setName("Root User");
		user.setUsername("root");
		user.setPassword(HashUtil.calculateMD5("12345"));
		user.setRoot(true);
		userDao2.create(user);
		
	}
	
	private void createUserRoleInitialData() {
		RuntimeExceptionDao<UserRole, Integer> userRoleDao2 = getUserRoleDao();
		UserRole ur1 = new UserRole();
		UserRole ur2 = new UserRole();
		UserRole ur3 = new UserRole();
		UserRole ur4 = new UserRole();
		UserRole ur5 = new UserRole();
		UserRole ur6 = new UserRole();
		UserRole ur7 = new UserRole();
		UserRole ur8 = new UserRole();
		UserRole ur9 = new UserRole();
		UserRole ur10 = new UserRole();
		UserRole ur11 = new UserRole();
		UserRole ur12 = new UserRole();
		UserRole ur13 = new UserRole();
		
		ur1.setUserId(1);
		ur1.setRoleId(Role.PRODUCTS_ROLE);
		ur1.setPermission(0xf);
		
		ur2.setUserId(2);
		ur2.setRoleId(Role.CLIENTS_ROLE);
		ur2.setPermission(0xf);
		
		ur3.setUserId(3);
		ur3.setRoleId(Role.PRODUCTS_ROLE);
		ur3.setPermission(0xf);
		
		ur4.setUserId(3);
		ur4.setRoleId(Role.CLIENTS_ROLE);
		ur4.setPermission(0xf);
		
		ur5.setUserId(4);
		ur5.setRoleId(Role.PRODUCTS_ROLE);
		ur5.setPermission(0x4);
		
		ur6.setUserId(4);
		ur6.setRoleId(Role.CLIENTS_ROLE);
		ur6.setPermission(0xf);
		
		ur7.setUserId(5);
		ur7.setRoleId(Role.PRODUCTS_ROLE);
		ur7.setPermission(0xf);
		
		ur8.setUserId(5);
		ur8.setRoleId(Role.CLIENTS_ROLE);
		ur8.setPermission(0x4);
		
		ur9.setUserId(6);
		ur9.setRoleId(Role.PRODUCTS_ROLE);
		ur9.setPermission(0x4);
		
		ur10.setUserId(6);
		ur10.setRoleId(Role.CLIENTS_ROLE);
		ur10.setPermission(0x4);
		
		ur11.setUserId(7);
		ur11.setRoleId(Role.PRODUCTS_ROLE);
		ur11.setPermission(0xf);
		
		ur12.setUserId(7);
		ur12.setRoleId(Role.CLIENTS_ROLE);
		ur12.setPermission(0xf);
		
		ur13.setUserId(7);
		ur13.setRoleId(Role.USERS_ROLE);
		ur13.setPermission(0xf);
		
		userRoleDao2.create(ur1);
		userRoleDao2.create(ur2);
		userRoleDao2.create(ur3);
		userRoleDao2.create(ur4);
		userRoleDao2.create(ur5);
		userRoleDao2.create(ur6);
		userRoleDao2.create(ur7);
		userRoleDao2.create(ur8);
		userRoleDao2.create(ur9);
		userRoleDao2.create(ur10);
		userRoleDao2.create(ur11);
		userRoleDao2.create(ur12);
		userRoleDao2.create(ur13);
	}
}
