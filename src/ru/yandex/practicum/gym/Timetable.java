package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, Group>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek nameDayOfWeek = trainingSession.getDayOfWeek();
        TreeMap<TimeOfDay, Group> timeOfDayGroupTreeMap;

        if (timetable.get(nameDayOfWeek) == null) {
            timeOfDayGroupTreeMap = new TreeMap<>();
            timeOfDayGroupTreeMap.put(trainingSession.getTimeOfDay(), trainingSession.getGroup());
            timetable.put(nameDayOfWeek, timeOfDayGroupTreeMap);
        } else {
            timeOfDayGroupTreeMap = timetable.get(nameDayOfWeek);
            timeOfDayGroupTreeMap.put(trainingSession.getTimeOfDay(), trainingSession.getGroup());
        }

    }

    public Map<TimeOfDay, Group> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {

        return timetable.get(dayOfWeek);
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public Group getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, Group> sessionsForDayAndTime = timetable.get(dayOfWeek);

        if (sessionsForDayAndTime == null) {
            return null;
        }

        return sessionsForDayAndTime.get(timeOfDay);
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }
}
