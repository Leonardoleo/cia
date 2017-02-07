/*
 * Copyright 2010 James Pether Sörling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	$Id$
 *  $HeadURL$
*/
package com.hack23.cia.service.component.agent.impl.riksdagen.workgenerator;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hack23.cia.model.external.riksdagen.personlista.impl.PersonElement;
import com.hack23.cia.model.internal.application.data.impl.RiksdagenDataSources;
import com.hack23.cia.service.external.riksdagen.api.RiksdagenPersonApi;

/**
 * The Class RiksdagenPersonsWorkGeneratorImpl.
 */
@Service
final class RiksdagenPersonsWorkGeneratorImpl extends AbstractRiksdagenDataSourcesWorkGenerator {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RiksdagenPersonsWorkGeneratorImpl.class);

	/** The person element workdestination. */
	@Autowired
	@Qualifier("com.hack23.cia.model.external.riksdagen.personlista.impl.PersonElement")
	private Destination personElementWorkdestination;

	/** The riksdagen api. */
	@Autowired
	private RiksdagenPersonApi riksdagenApi;

	/**
	 * Instantiates a new riksdagen persons work generator impl.
	 */
	public RiksdagenPersonsWorkGeneratorImpl() {
		super(RiksdagenDataSources.PERSONS);
	}

	@Override
	public void generateWorkOrders() {
		try {
			final List<PersonElement> personList = riksdagenApi.getPersonList().getPerson();
			final Map<String, String> currentSaved = getImportService().getPersonMap();

			for (final PersonElement personElement : personList) {
				if (!currentSaved.containsKey(personElement.getId())) {
					LOGGER.info("Send Load Order:{}", personElement.getPersonUrlXml());
					sendMessage(personElementWorkdestination, personElement);
					currentSaved.put(personElement.getId(), personElement.getId());
				}
			}
			for (final String personId : readMissingPersonList()) {
				if (!currentSaved.containsKey(personId)) {
					LOGGER.info("Send Load Order:{}{}", "http://data.riksdagen.se/person/", personId);
					sendMessage(personElementWorkdestination, new PersonElement().withId(personId));
				}
			}
		} catch (final Exception exception) {
			LOGGER.warn("jms", exception);
		}
	}

	/**
	 * Read missing person list.
	 *
	 * @return the string[]
	 */
	private static String[] readMissingPersonList() {

		final Scanner sc = new Scanner(RiksdagenPersonsWorkGeneratorImpl.class.getResourceAsStream("/personlist.txt"),StandardCharsets.UTF_8.name());
		final List<String> lines = new ArrayList<>();
		while (sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}
		sc.close();
		return lines.toArray(new String[0]);
	}

}
