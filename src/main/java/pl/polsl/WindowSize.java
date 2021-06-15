package pl.polsl;

public enum WindowSize {
    //ADMINISTRATOR
    adminMenuForm (0,0),
    //class
    manageClassForm (350,450),
    addOrUpdateClassForm(450,300),
    //subject
    manageSubjectsForm(500,450),
    addOrUpdateSubjectForm(400,280),
    //teacher
    manageTeachersForm (0, 0),
    addOrUpdateTeacherForm(470,410),
    //parent
    manageParentsForm (500, 450),
    addOrUpdateParentForm(450,600);

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
