/*
 * Copyright 2014 James Pether Sörling
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
package com.hack23.cia.web.impl.ui.application.views.user.test.pagemode;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.chartfactory.DocumentChartDataManager;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.ChartIndicators;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.PageMode;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class TestChartsDecisionActivityByTypePageModContentFactoryImpl.
 */
@Component
public final class TestChartsDecisionActivityByTypePageModContentFactoryImpl extends AbstractTestPageModContentFactoryImpl {

	private static final String PARTY_WINNER_DAILY_AVERAGE_FOR_ALL_BALLOTS = "Party Winner, daily average for all ballots";

	/** The document chart data manager. */
	@Autowired
	private transient DocumentChartDataManager documentChartDataManager;

	/**
	 * Instantiates a new test charts decision activity by type page mod content
	 * factory impl.
	 */
	public TestChartsDecisionActivityByTypePageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page) && !StringUtils.isEmpty(parameters) && parameters.contains(PageMode.CHARTS.toString())
				&& parameters.contains(ChartIndicators.DECSIONACTIVITYBYTYPE.toString());
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();

		final String pageId = getPageId(parameters);

		panelContent.addComponent(documentChartDataManager.createDecisionTypeChart());

		getPageActionEventHelper().createPageEvent(ViewAction.VISIT_TEST_CHART_VIEW, ApplicationEventGroup.USER, NAME,
				parameters, pageId);
		panel.setCaption(PARTY_WINNER_DAILY_AVERAGE_FOR_ALL_BALLOTS);

		return panelContent;

	}

}
