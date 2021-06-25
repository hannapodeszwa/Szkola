package pl.polsl.utils;

public enum WindowSize {//należy dodać do okienka z scenbuildera w: +15, h: +40
    //ADMINISTRATOR
    adminMenuForm (205,491),
    //class
    manageClassForm (321,340),
    addOrUpdateClassForm(305,251),
    //schedule
    manageScheduleForm(971,630),
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
    teacherMenuForm(205,411),
    teacherAbsenceForm(545,410),
    teacherAddNewAbsenceForm(385,291),
    teacherAddNewClubForm(245,251),
    teacherAddNewCompetitionForm(245, 320),
    teacherAddNewGradeForm(375,351),
    teacherAddNewNoteForm(375,265),
    TeacherAssignStudentToClubForm(461, 306),
    TeacherAssignStudentToCompetitionForm(461, 306),
    teacherClubForm(461,406),
    teacherCompetitionForm(461,387),
    teacherGradeForm(501,465),
    teacherNoteForm(461,376),
    teacherScheduleForm(926,440),
    //parent
    manageParentsForm (571, 492),
    addOrUpdateParentForm(371,543),
    parentMenuForm(205,331),
    //classroom
    manageClassroomsForm(441,335),
    addOrUpdateClassroomForm(345,251),
    //student
    studentMenuForm(205,411),
    studentAbsenceForm(471,402),
    studentGradesForm(541,402),
    studentScheduleForm(971,606),
    studentClubsForm(391, 373),
    studentCompetitionsForm(591, 375),
    studentNoteForm(425, 415),
    //common
    changePasswordForm(205,280),
    messageForm(855,440),
    messagerForm(855,440),
    resetPasswordForm(205,385),
    viewMessageForm(855,440),
    signIn(205,290),
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
