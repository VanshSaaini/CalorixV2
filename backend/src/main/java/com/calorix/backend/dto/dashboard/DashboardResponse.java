package com.calorix.backend.dto.dashboard;

import com.calorix.backend.dto.bmi.BmiRecordResponse;
import com.calorix.backend.dto.bmr.BmrRecordResponse;
import com.calorix.backend.dto.bodymeasurement.BodyMeasurementResponse;
import com.calorix.backend.dto.calories.DailyCaloriesResponse;
import com.calorix.backend.dto.goal.GoalResponse;
import com.calorix.backend.dto.macro.MacroRecordResponse;
import com.calorix.backend.dto.progressphoto.ProgressPhotoResponse;
import com.calorix.backend.dto.user.UserResponse;
import com.calorix.backend.dto.water.WaterIntakeResponse;
import com.calorix.backend.dto.weight.WeightRecordResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private UserResponse user;

    private WeightRecordResponse latestWeight;

    private BodyMeasurementResponse latestMeasurement;

    private BmiRecordResponse latestBmi;

    private BmrRecordResponse latestBmr;

    private MacroRecordResponse latestMacros;

    private DailyCaloriesResponse latestCalories;

    private WaterIntakeResponse latestWater;

    private GoalResponse activeGoal;

    private ProgressPhotoResponse latestPhoto;
}