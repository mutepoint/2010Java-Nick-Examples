package HotelReservationSpring.util;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import HotelReservationSpring.pojos.Guest;
import HotelReservationSpring.pojos.Hotel;
import HotelReservationSpring.pojos.Room;

public class SessionFactoryUtil {
	
	private static SessionFactoryUtil sfu;
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}



	private SessionFactoryUtil() {
		
		if (sessionFactory == null) {
			
			Map<String, String> settings = new HashMap<>();
			settings.put("hibernate.connection.url", System.getenv("HOTEL_URL"));
			settings.put("hibernate.connection.username", System.getenv("HOTEL_USERNAME"));
			settings.put("hibernate.connection.password", System.getenv("HOTEL_PASSWORD"));
			settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
			settings.put("hibernate.connection.dialect", "org.hibernate.dialect.PostgreSQLDialect");
			//settings.put("hibernate.connection.driver_class", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//settings.put("hibernate.connection.dialect", "org.hibernate.dialect.SQLServer2012Dialect");
			//settings.put("hibernate.show_sql", "true");
			//settings.put("hibernate.format_sql", "true");
			//settings.put("hibernate.hbm2ddl.auto", "create");
			
			
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();
			
			Metadata metadata = new MetadataSources(standardRegistry)
						.addAnnotatedClass(Guest.class)
						.addAnnotatedClass(Room.class)
						.addAnnotatedClass(Hotel.class)
						.getMetadataBuilder()
						.build();
			
			sessionFactory = metadata.getSessionFactoryBuilder().build();
			
		}
		
	}
	
	public static SessionFactoryUtil getSessionFactoryUtil() {
		if (sfu == null) {
			sfu = new SessionFactoryUtil();
		}
		
		return sfu;
	}

}
