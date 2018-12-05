package com.gds.admin.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Airport.class)
public abstract class Airport_ {

	public static volatile SingularAttribute<Airport, Long> airportId;
	public static volatile SingularAttribute<Airport, String> cityCode;
	public static volatile SingularAttribute<Airport, String> countryCode;
	public static volatile SingularAttribute<Airport, Long> id;
	public static volatile SingularAttribute<Airport, String> airportCode;
	public static volatile SingularAttribute<Airport, Boolean> isDomestic;

}

