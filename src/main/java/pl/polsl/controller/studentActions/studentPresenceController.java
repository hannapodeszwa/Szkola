package pl.polsl.controller.studentActions;

import pl.polsl.controller.ParametrizedController;
import pl.polsl.controller.menu.StudentMenuController;

import java.util.Map;

public class studentPresenceController implements ParametrizedController {

    public enum md {Parent, Student}
    private StudentMenuController.md mode;

    @Override
    public void receiveArguments(Map params) {
        String a = (String)params.get("mode");
        if (a == "Parent") {
            mode = StudentMenuController.md.Parent;
        }
        else {
            mode = StudentMenuController.md.Student;
        }
    }
}
