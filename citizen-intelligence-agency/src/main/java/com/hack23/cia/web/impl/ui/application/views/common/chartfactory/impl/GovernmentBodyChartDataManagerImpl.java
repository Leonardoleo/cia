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
package com.hack23.cia.web.impl.ui.application.views.common.chartfactory.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYseries;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.options.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hack23.cia.service.external.esv.api.EsvApi;
import com.hack23.cia.service.external.esv.api.GovernmentBodyAnnualOutcomeSummary;
import com.hack23.cia.service.external.esv.api.GovernmentBodyAnnualSummary;
import com.hack23.cia.web.impl.ui.application.views.common.chartfactory.api.GovernmentBodyChartDataManager;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class GovernmentBodyChartDataManagerImpl.
 */
@Service
public final class GovernmentBodyChartDataManagerImpl extends AbstractChartDataManagerImpl
		implements GovernmentBodyChartDataManager {

	/** The Constant ANNUAL_HEADCOUNT_SUMMARY_ALL_GOVERNMENT_BODIES. */
	private static final String ANNUAL_HEADCOUNT_SUMMARY_ALL_GOVERNMENT_BODIES = " Annual headcount summary, all government bodies";

	/** The Constant ANNUAL_HEADCOUNT_ALL_MINISTRIES. */
	private static final String ANNUAL_HEADCOUNT_ALL_MINISTRIES = "Annual headcount, all ministries";

	/** The Constant FIRST_OF_JAN. */
	private static final String FIRST_OF_JAN = "01-JAN-";

	/** The esv api. */
	@Autowired
	private EsvApi esvApi;

	/**
	 * Instantiates a new government body chart data manager impl.
	 */
	public GovernmentBodyChartDataManagerImpl() {
		super();
	}

	@Override
	public void createMinistryGovernmentBodyHeadcountSummaryChart(final AbstractOrderedLayout content,
			final String name) {
		final Map<Integer, List<GovernmentBodyAnnualSummary>> map = esvApi.getDataPerMinistry(name);
		final List<String> governmentBodyNames = esvApi.getGovernmentBodyNames(name);

		final DataSeries dataSeries = new DataSeries();
		final Series series = new Series();

		for (final String govBodyName : governmentBodyNames) {

			series.addSeries(new XYseries().setLabel(govBodyName));

			dataSeries.newSeries();

			for (final Entry<Integer, List<GovernmentBodyAnnualSummary>> entry : map.entrySet()) {

				final List<GovernmentBodyAnnualSummary> item = entry.getValue();
				final Integer totalHeadcount = item.stream()
						.filter((final GovernmentBodyAnnualSummary p) -> p.getName().equalsIgnoreCase(govBodyName))
						.mapToInt(GovernmentBodyAnnualSummary::getHeadCount).sum();

				if (entry.getKey() != null && totalHeadcount > 0) {
					dataSeries.add(FIRST_OF_JAN + entry.getKey(), totalHeadcount);
				}
			}
		}

		addChart(content, name + ANNUAL_HEADCOUNT_SUMMARY_ALL_GOVERNMENT_BODIES,
				new DCharts().setDataSeries(dataSeries)
						.setOptions(getChartOptions().createOptionsXYDateFloatLogYAxisLegendOutside(series)).show(),
				true);

	}

	@Override
	public void createMinistryGovernmentBodyHeadcountSummaryChart(final AbstractOrderedLayout content) {
		final Map<Integer, List<GovernmentBodyAnnualSummary>> map = esvApi.getData();
		final List<String> ministryNames = esvApi.getMinistryNames();

		final DataSeries dataSeries = new DataSeries();

		final Series series = new Series();

		for (final String ministryName : ministryNames) {

			series.addSeries(new XYseries().setLabel(ministryName));

			dataSeries.newSeries();

			for (final Entry<Integer, List<GovernmentBodyAnnualSummary>> entry : map.entrySet()) {

				final List<GovernmentBodyAnnualSummary> item = entry.getValue();
				final Integer totalHeadcount = item.stream()
						.filter((final GovernmentBodyAnnualSummary p) -> p.getMinistry().equalsIgnoreCase(ministryName))
						.mapToInt(GovernmentBodyAnnualSummary::getHeadCount).sum();

				if (entry.getKey() != null && totalHeadcount > 0) {
					dataSeries.add(FIRST_OF_JAN + entry.getKey(), totalHeadcount);
				}
			}
		}

		addChart(content, ANNUAL_HEADCOUNT_ALL_MINISTRIES,
				new DCharts().setDataSeries(dataSeries)
						.setOptions(getChartOptions().createOptionsXYDateFloatLogYAxisLegendOutside(series)).show(),
				true);

	}

	@Override
	public void createGovernmentBodyHeadcountSummaryChart(VerticalLayout content) {
		final Map<Integer, List<GovernmentBodyAnnualSummary>> map = esvApi.getData();

		final DataSeries dataSeries = new DataSeries();

		final Series series = new Series();

		series.addSeries(new XYseries().setLabel("All government bodies"));

		dataSeries.newSeries();

		for (final Entry<Integer, List<GovernmentBodyAnnualSummary>> entry : map.entrySet()) {

			final List<GovernmentBodyAnnualSummary> item = entry.getValue();
			final Integer totalHeadcount = item.stream().mapToInt(GovernmentBodyAnnualSummary::getHeadCount).sum();

			if (entry.getKey() != null && totalHeadcount > 0) {
				dataSeries.add(FIRST_OF_JAN + entry.getKey(), totalHeadcount);
			}
		}

		addChart(content, "Annual headcount total all government bodies",
				new DCharts().setDataSeries(dataSeries)
						.setOptions(getChartOptions().createOptionsXYDateFloatLogYAxisLegendOutside(series)).show(),
				true);

	}

	@Override
	public void createGovernmentBodyHeadcountSummaryChart(VerticalLayout content, String name) {
		Map<Integer, GovernmentBodyAnnualSummary> map = esvApi.getDataPerGovernmentBody(name);

		final DataSeries dataSeries = new DataSeries();
		final Series series = new Series();

		series.addSeries(new XYseries().setLabel(name));

		dataSeries.newSeries();

		for (final Entry<Integer, GovernmentBodyAnnualSummary> entry : map.entrySet()) {

			final GovernmentBodyAnnualSummary item = entry.getValue();
			final Integer totalHeadcount = item.getHeadCount();

			if (entry.getKey() != null && totalHeadcount > 0) {
				dataSeries.add(FIRST_OF_JAN + entry.getKey(), totalHeadcount);
			}
		}

		addChart(content, name + "Annual headcount",
				new DCharts().setDataSeries(dataSeries)
						.setOptions(getChartOptions().createOptionsXYDateFloatLogYAxisLegendOutside(series)).show(),
				true);
	}

	@Override
	public void createGovernmentBodyIncomeSummaryChart(VerticalLayout panelContent) {
		Map<String, List<GovernmentBodyAnnualOutcomeSummary>> report = esvApi
				.getGovernmentBodyReportByField("Inkomsttitelgruppsnamn");
	}

	@Override
	public void createGovernmentBodyExpenditureSummaryChart(VerticalLayout panelContent) {
		Map<String, List<GovernmentBodyAnnualOutcomeSummary>> report = esvApi
				.getGovernmentBodyReportByField("Utgiftsområdesnamn");
	}

	@Override
	public void createGovernmentBodyIncomeSummaryChart(VerticalLayout content, String name) {
		List<GovernmentBodyAnnualOutcomeSummary> report = esvApi.getGovernmentBodyReport().get(name);

		Map<String, List<GovernmentBodyAnnualOutcomeSummary>> collect = report.stream()
				.filter(p -> p.getDescriptionFields().get("Inkomsttitelsnamn") != null)
				.collect(Collectors.groupingBy(t -> t.getDescriptionFields().get("Inkomsttitelsnamn")));

		final DataSeries dataSeries = new DataSeries();
		final Series series = new Series();

		for (Entry<String, List<GovernmentBodyAnnualOutcomeSummary>> entry : collect.entrySet()) {
			series.addSeries(new XYseries().setLabel(entry.getKey()));
			dataSeries.newSeries();

			for (GovernmentBodyAnnualOutcomeSummary data : entry.getValue()) {
				if (data != null && data.getYearTotal().intValue() > 0) {
					dataSeries.add(data.getYear() + "-01-01", data.getYearTotal().intValue());
				}
			}
		}

		addChart(content, name + "Annual Income",
				new DCharts().setDataSeries(dataSeries)
						.setOptions(getChartOptions().createOptionsXYDateFloatLogYAxisLegendOutside(series)).show(),
				true);

	}

	@Override
	public void createGovernmentBodyExpenditureSummaryChart(VerticalLayout content, String name) {
		List<GovernmentBodyAnnualOutcomeSummary> report = esvApi.getGovernmentBodyReport().get(name);

		Map<String, List<GovernmentBodyAnnualOutcomeSummary>> collect = report.stream()
				.filter(p -> p.getDescriptionFields().get("Anslagspostsnamn") != null)
				.collect(Collectors.groupingBy(t -> t.getDescriptionFields().get("Anslagspostsnamn")));

		final DataSeries dataSeries = new DataSeries();
		final Series series = new Series();

		for (Entry<String, List<GovernmentBodyAnnualOutcomeSummary>> entry : collect.entrySet()) {
			series.addSeries(new XYseries().setLabel(entry.getKey()));
			dataSeries.newSeries();

			for (GovernmentBodyAnnualOutcomeSummary data : entry.getValue()) {
				if (data != null && data.getYearTotal().intValue() > 0) {
					dataSeries.add(data.getYear() + "-01-01", data.getYearTotal().intValue());
				}
			}
		}

		addChart(content, name + "Annual Expenditure",
				new DCharts().setDataSeries(dataSeries)
						.setOptions(getChartOptions().createOptionsXYDateFloatLogYAxisLegendOutside(series)).show(),
				true);

	}

}
