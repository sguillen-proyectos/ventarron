package org.inf325.ventarron;

import org.inf325.ventarron.dao.User;

/**
 * Kind of useless class in order 
 * to get the initial activity 
 * for a specific role.
 * 
 * I'm 99% sure it can be done
 * on a more elegant way ¬¬
 * @author donkeysharp
 *
 */
public class RoleActivityMap {
	public static Class<?> getActivityClass(int role) {
		switch(role) {
			case User.ROLE_DEPOT:
				return ProductListActivity.class;
			case User.ROLE_SELLER:
				// TODO: It's just temporal,
				// it has to be a menu or client activity list
				return ClientListActivity.class;
		}
		return null;
	}
}
