package pl.polsl.utils;

public enum WindowSize {//należy dodać do okienka z scenbuildera w: +15, h: +40
    //ADMINISTRATOR
    adminMenuForm (275,560),
    //class
    manageClassForm (350,450),
    addOrUpdateClassForm(450,300),
    //schedule
    manageScheduleForm(985,710),
    manageLessonHoursForm(420,420),
    //subject
    manageSubjectsForm(500,450),
    addOrUpdateSubjectForm(400,280),
    //student
    manageStudentsForm(593, 435),
    addOrUpdateStudentForm(435, 435),
    //teacher
    manageTeachersForm (600, 450),
    addOrUpdateTeacherForm(470,410),
    teacherScheduleForm(926,445),
    teacherNoteForm(461,376),
    teacherAddNewNoteForm(405,265),
    //parent
    manageParentsForm (580, 500),
    addOrUpdateParentForm(450,600),
    //classroom
    manageClassroomsForm(500,450),
    addOrUpdateClassroomForm(400,280),
    //student
    studentPresenceForm(471,402),
    studentGradesForm(531,402),
    studentScheduleForm(985,710)

    ;


    private static final int defaultSize = 600;
    private final int width;
    private final int height;

    WindowSize(final int w,final int h) {
        width=w;
        height=h;
    }
    public int getWidth() {
        if(width!=0)
        return width;
        return defaultSize;
    }

    public int getHeight() {
        if(height!=0)
        return height;
        return defaultSize;
    }
}
