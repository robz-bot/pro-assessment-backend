package com.promantus.Assessment.Service;

import java.util.List;

import com.promantus.Assessment.Dto.WidgetDto;

public interface DashboardService {
	List<WidgetDto> widgetData() throws Exception;

}
