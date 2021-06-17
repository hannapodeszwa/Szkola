package pl.polsl;

public enum WindowSize {//należy dodać do okienka z scenbuildera w: +15, h: +40
    //ADMINISTRATOR
    adminMenuForm (275,480),
    //class
    manageClassForm (350,450),
    addOrUpdateClassForm(450,300),
    //schedule
    manageScheduleForm(1025,510),
    //subject
    manageSubjectsForm(500,450),
    addOrUpdateSubjectForm(400,280),
    //student
    manageStudentsForm(593, 435),
    addOrUpdateStudentForm(435, 435),
    //teacher
    manageTeachersForm (600, 450),
    addOrUpdateTeacherForm(470,410),
    //parent
    manageParentsForm (550, 450),
    addOrUpdateParentForm(450,600),
    //student
    studentPresenceForm(471,402),
    studentGradesForm(531,402),
    studentScheduleForm(926,445)

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
