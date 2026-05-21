package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.html.HTMLBaseElement;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        int oneSessionOnMonday = 1;
        //Проверить, что за понедельник вернулось одно занятие
        //Проверить, что за вторник не вернулось занятий
        Map<TimeOfDay, Group> sessionsMondayGroup = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Map<TimeOfDay, Group>  sessionsTuesdayGroup = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertEquals(oneSessionOnMonday, sessionsMondayGroup.size());
        Assertions.assertNull(sessionsTuesdayGroup);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        int oneSessionOnMonday = 1;
        int twoSessionsOnThursday = 2;
        int sessionIn13Clock = 13;
        int sessionIn20Clock = 20;

        // Проверить, что за понедельник вернулось одно занятие
        Map<TimeOfDay, Group> sessionsMondayGroup = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(oneSessionOnMonday, sessionsMondayGroup.size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Map<TimeOfDay, Group> sessionsThursdayGroup = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(twoSessionsOnThursday, sessionsThursdayGroup.size());

        List<Map.Entry<TimeOfDay, Group>> entries = new ArrayList<>(sessionsThursdayGroup.entrySet());
        Assertions.assertEquals(sessionIn13Clock, entries.get(0).getKey().getHours());
        Assertions.assertEquals(Age.CHILD, entries.get(0).getValue().getAge());

        Assertions.assertEquals(sessionIn20Clock, entries.get(1).getKey().getHours());
        Assertions.assertEquals(Age.ADULT, entries.get(1).getValue().getAge());

        // Проверить, что за вторник не вернулось занятий
        Map<TimeOfDay, Group> sessionsTuesdayGroup = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertNull(sessionsTuesdayGroup);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSessionTime13 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSessionTime13);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        TimeOfDay time13 = new TimeOfDay(13, 0);
        Group sessionsOfTime13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, time13);
        Assertions.assertNotNull(sessionsOfTime13);

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        TimeOfDay time14 = new TimeOfDay(14,0);
        Group sessionsOfTime14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, time14);
        Assertions.assertNull(sessionsOfTime14);
    }

}
