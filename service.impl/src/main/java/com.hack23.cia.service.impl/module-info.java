module com.hack23.cia.service.impl {


	exports com.hack23.cia.service.impl;
	exports com.hack23.cia.service.impl.rules;
	
	opens com.hack23.cia.service.impl to spring.core, spring.beans, spring.context;
	opens com.hack23.cia.service.impl.action.admin to spring.core, spring.beans, spring.context;
	opens com.hack23.cia.service.impl.action.application to spring.core, spring.beans, spring.context;
	opens com.hack23.cia.service.impl.action.common to spring.core, spring.beans, spring.context;
	opens com.hack23.cia.service.impl.action.kpi to spring.core, spring.beans, spring.context;
	opens com.hack23.cia.service.impl.action.user to spring.core, spring.beans, spring.context;
	
	opens com.hack23.cia.service.impl.email to spring.core, spring.beans, spring.context;
	opens com.hack23.cia.service.impl.rules to spring.core, spring.beans, spring.context;
	opens com.hack23.cia.service.impl.task to spring.core, spring.beans, spring.context;
	
	requires java.validation;
	requires java.xml.bind;
	requires java.annotation;
	requires org.slf4j;
	requires spring.context;
	requires spring.beans;
	requires spring.tx;
	requires spring.jms;
	requires spring.messaging;
	requires spring.context.support;
	requires quartz;
	requires commons.lang;
	requires com.google.common;

	requires googleauth;

	requires spring.security.core;
	requires spring.core;
	requires org.apache.commons.lang3;

	requires weka.dev;
	requires passay;

	requires org.joda.time;

	requires jms;
	requires java.mail;
	requires org.kie.api;
	requires org.drools.core;
	requires org.bouncycastle.pkix;
	requires org.bouncycastle.provider;

	requires com.hack23.cia.service.api;
	
	requires com.hack23.cia.service.component.agent.api;
	requires com.hack23.cia.service.component.agent.impl;

	requires com.hack23.cia.service.external.common;
    requires com.hack23.cia.service.data.api;
	requires com.hack23.cia.service.external.riksdagen;
	requires com.hack23.cia.service.external.val;
	requires com.hack23.cia.service.external.worldbank;

	requires com.hack23.cia.model.external.val.riksdagsvalkrets.impl;
	requires com.hack23.cia.model.external.riksdagen.voteringlista.impl;
	requires com.hack23.cia.model.external.val.kommunvalkrets.impl;
	requires com.hack23.cia.model.external.worldbank.topic.impl;
	requires com.hack23.cia.model.external.worldbank.indicators.impl;
	requires com.hack23.cia.model.external.riksdagen.documentcontent.impl;
	requires com.hack23.cia.model.external.worldbank.data.impl;
	requires com.hack23.cia.model.external.riksdagen.person.impl;
	requires com.hack23.cia.model.external.riksdagen.dokumentstatus.impl;
	requires com.hack23.cia.model.external.riksdagen.dokumentlista.impl;
	requires com.hack23.cia.model.external.riksdagen.utskottsforslag.impl;
	requires com.hack23.cia.model.external.val.partier.impl;
	requires com.hack23.cia.model.external.riksdagen.personlista.impl;
	requires com.hack23.cia.model.external.val.landstingvalkrets.impl;
	requires com.hack23.cia.model.external.riksdagen.votering.impl;
}