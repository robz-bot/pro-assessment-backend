package com.promantus.Assessment.Service;

import java.util.List;
import java.util.Map;

import com.promantus.Assessment.Dto.WidgetDto;

public interface DashboardService {
	List<WidgetDto> widgetData() throws Exception;

	Map<Object, Object> userAttemptsChart()throws Exception;

	Map<Object, Object> datewisePassFail(String date)throws Exception;

	Map<Object, Object> teamExamReadiness()throws Exception;

	Map<Object, Object> questionsActiveInactive()throws Exception;

}
