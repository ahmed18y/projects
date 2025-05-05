package projectdsa;   


// action class
class ActionNode {
    String actionType;
    int studentID, courseID;
    ActionNode next;

    ActionNode(String actionType, int studentID, int courseID) {
        this.actionType = actionType;
        this.studentID = studentID;
        this.courseID = courseID;
    }
}

class ActionStack {
    ActionNode top;

    void push(String actionType, int studentID, int courseID) {
        ActionNode node = new ActionNode(actionType, studentID, courseID);
        node.next = top;
        top = node;
    }

    ActionNode pop() {
        if (top == null){ return null;}
        ActionNode temp = top;
        top = top.next;
        return temp;
    }

    void clear() {
        top = null;
    }

    boolean isEmpty() {
        return top == null;
    }
}





// node class *AHMED YOUSSEF*
class nodeStudent {
    int ID;
    courseLLS mycourses;
    nodeStudent next;

    nodeStudent(int ID) {
        this.ID = ID;
        mycourses = new courseLLS(); //creates an empty course list for this student
        next = null;
    }
}
//end of node class*A*



// Linked List class for Students*AHMED YOUSSEF*
class studentsLLS {
    nodeStudent head;
    nodeStudent tail;
    static int noStudents;

    ActionStack undoStack = new ActionStack();
    ActionStack redoStack = new ActionStack();


//method to add student *AHMED YOUSSEF*
    public void addStudents(int ID) {
        nodeStudent n = new nodeStudent(ID);
        if (head == null) {
            //يعني لو فاضيه 
            head = tail = n;
        } else {
            tail.next = n;
            tail = n;
        }
        noStudents++;
    }   //the end of add student method *a*
    
    
    
    //mehod to remove student *SHAHEEN*
 public void removeStudent(int ID) {
        if (head == null) {
            System.out.println("EMPTY LIST ");
            return;
        }
        if (head.ID == ID) {
            head = head.next;
            if (head == null) tail = null;
            noStudents--;
            return;
        }

        nodeStudent current = head;
        while (current.next != null) {
            if (current.next.ID == ID) {
                current.next = current.next.next;
                if (current.next == null) tail = current;
                noStudents--;
                return;
            }
            current = current.next;
        }
        System.out.println("Student not found: ");
    }
 //end of remove student method*SH*
 
 
// method that return the last student in the LL* SHAHEEN*
    public void lastStudent() {
        if (tail != null) {
            System.out.println("TheID Of last student is: " + tail.ID);
        } else {
            System.out.println("No students in the list.");
        }
    }

    
    public nodeStudent findStudent(int ID) {
        nodeStudent temp = head;
        while (temp != null) {
            if (temp.ID == ID) {
                return temp;
            }
            temp = temp.next;
        }
        return null; 
    }

    public void listCoursesByStudent(int studentID) {
        nodeStudent student = findStudent(studentID);
        if (student != null) {
            student.mycourses.printCourses();
        } else {
            System.out.println("Student not found.");
        }
    }

    public void enrollStudentInCourse(int studentID, int courseID) {
        nodeStudent student = findStudent(studentID);
        if (student != null) {
            student.mycourses.addcourse(courseID);
            undoStack.push("enroll", studentID, courseID);
            redoStack.clear();
        } else {
            System.out.println("Student not found.");
        }
    }

    public void removeEnrollment(int studentID, int courseID) {
        nodeStudent student = findStudent(studentID);
        if (student != null) {
            student.mycourses.removecourse(courseID);
            undoStack.push("remove", studentID, courseID);
            redoStack.clear();
        } else {
            System.out.println("Student not found.");
        }
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }
        ActionNode action = undoStack.pop();
        if (action.actionType.equals("enroll")) {
            findStudent(action.studentID).mycourses.removecourse(action.courseID);
            redoStack.push("enroll", action.studentID, action.courseID);
        } else {if (action.actionType.equals("remove")) {
            findStudent(action.studentID).mycourses.addcourse(action.courseID);
            redoStack.push("remove", action.studentID, action.courseID);
        }}
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo.");
            return;
        }
        ActionNode action = redoStack.pop();
        if (action.actionType.equals("enroll")) {
            findStudent(action.studentID).mycourses.addcourse(action.courseID);
            undoStack.push("enroll", action.studentID, action.courseID);
        } else if (action.actionType.equals("remove")) {
            findStudent(action.studentID).mycourses.removecourse(action.courseID);
            undoStack.push("remove", action.studentID, action.courseID);
        }
    }

    public void printstudents() {
        if (head == null) {
            System.out.println("No students in the list.");
            return;
        }
        nodeStudent temp = head;
        while (temp != null) {
            System.out.println("Student ID: " + temp.ID);
            System.out.print("Courses: ");
            temp.mycourses.printCourses();
            temp = temp.next;
        }
    }

    public void listStudentsByCourse(int courseID) {
        nodeStudent temp = head;
        while (temp != null) {
            if (temp.mycourses.isEnrolled(courseID)) {
                System.out.println("Student ID: " + temp.ID);
            }
            temp = temp.next;
        }
    }

    public void isfullCourse(int courseID) {
        int count = 0;
        nodeStudent temp = head;
        while (temp != null) {
            if (temp.mycourses.isEnrolled(courseID)) {
                count++;
            }
            temp = temp.next;
        }
        if (count >= 30) {
            System.out.println("Course " + courseID + " is full.");
        } else {
            System.out.println("Course " + courseID + " is not full.");
        }
    }

    public void isnormalstudent(int studentID) {
        nodeStudent student = findStudent(studentID);
        if (student != null) {
            int courseCount = student.mycourses.countCourses();
            if (courseCount >= 2 && courseCount <= 7) {
                System.out.println("Student " + studentID + " is a normal student.");
            } else {
                System.out.println("Student " + studentID + " is not a normal student.");
            }
        } else {
            System.out.println("Student not found.");
        }
    }

    
    
    
    //method to sorting students with Id*AHMEDD YOUSSEF*
    public void sortStudents() {
        if (head == null || head.next == null) {return;}
        for (nodeStudent i = head; i != null; i = i.next) {
            for (nodeStudent j = i.next; j != null; j = j.next) {
                if (i.ID > j.ID) {
                    int temp = i.ID;
                    courseLLS tempCourses = i.mycourses;
                    i.ID = j.ID;
                    i.mycourses = j.mycourses;
                    j.ID = temp;
                    j.mycourses = tempCourses;
                }
            }
        }
    }
}






// Node class for Course*AHMED YOUSSEF*
class nodeCourse {
    int ID;
    nodeCourse next;

    nodeCourse(int ID) {
        this.ID = ID;
        this.next = null;
    }
}
// Linked List class for Courses*AHMED YOUSSEF*
class courseLLS {
    nodeCourse head;
    nodeCourse tail;
    static int nocourses;
//method to add courses to the LL*AHMED YOUSSEF*
    public void addcourse(int id) {
        nodeCourse nn = new nodeCourse(id);
        if (head == null) {
            head = tail = nn;
        } else {
            tail.next = nn;
            tail = nn;
        }
        nocourses++;
    }
    //the end of method that adding courses*A*
    
    
//method to remove courses *SHAHEEN*
    public void removecourse(int id) {
        if (head == null) {
            System.out.println("EMPTY LIST");
            return;
        }
        if (head.ID == id) {
            head = head.next;
            if (head == null) tail = null;
            nocourses--;
            return;
        }

        nodeCourse current = head;
        while (current.next != null) {
            if (current.next.ID == id) {
                current.next = current.next.next;
                if (current.next == null) tail = current;
                nocourses--;
                return;
            }
            current = current.next;
        }
        System.out.println("Course not found: " + id);
    }
    
    
    
 // method that return the last course in the LL* SHAHEEN*
    public void lastcourse() {
        if (tail != null) {
            System.out.println("The ID of last course is: " + tail.ID);
        } else {
            System.out.println("No courses in the list.");
        }
    }

    public boolean isEnrolled(int courseID) {
        nodeCourse temp = head;
        while (temp != null) {
            if (temp.ID == courseID) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    
    
//method to print all courses in LL* AHMED YOUSSEF *
    public void printCourses() {
        if (head == null) {
            System.out.println("No courses enrolled.");
            return;
        }
        nodeCourse temp = head;
        while (temp != null) {
            System.out.print(temp.ID + " -> ");
            temp = temp.next;
        }
        System.out.println("null");
    }
 //the end of method that print all courses * A *


    
//method that return no# courses *ADAM*
    public int countCourses() {
        int count = 0;
        nodeCourse temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    
    //method to sorting courses with Id*AHMED YOUSSEF*
    public void sortCoursesByID() {
        if (head == null || head.next == null){ return;}
        for (nodeCourse i = head; i != null; i = i.next) {
            for (nodeCourse j = i.next; j != null; j = j.next) {
                if (i.ID > j.ID) {
                    int temp = i.ID;
                    i.ID = j.ID;
                    j.ID = temp;
                }
            }
        }
    }
}








//Maaaaiiiiiiinnnnn
public class ProjectDSA {
    public static void main(String[] args) {
        studentsLLS S1 = new studentsLLS();
        courseLLS  c1=new courseLLS();
       
S1.addStudents(1001);
S1.addStudents(1002);
S1.addStudents(1003);

         
c1.addcourse(44);
c1.addcourse(89);




S1.enrollStudentInCourse(1001, 501);
S1.enrollStudentInCourse(1001, 502);
S1.enrollStudentInCourse(1002, 501);
S1.enrollStudentInCourse(1003, 503);



System.out.println("After sorting students:");
S1.sortStudents();
S1.printstudents();


System.out.println("Sorting courses for student 1001:");
nodeStudent tempStudent = S1.findStudent(1001);
if (tempStudent != null) {
    tempStudent.mycourses.sortCoursesByID();
    tempStudent.mycourses.printCourses();
}


// Print all courses the student enrolled in
         S1.listCoursesByStudent(1001);

// Undo operation
System.out.println("Undoing last operation:");
S1.undo();
System.out.println("Courses for student 1002 after undo:");
S1.listCoursesByStudent(1002);

// Redo operation
System.out.println("Redoing last operation:");
S1.redo();
System.out.println("Courses for student 1002 after redo:");
S1.listCoursesByStudent(1002);

 // Print all students
         S1.printstudents();
         // Print all students enrolled in course 501
         S1.listStudentsByCourse(501);
         // Checks if the course is full or not
         S1.isfullCourse(501);
         // Checks if student is normal or not
         S1.isnormalstudent(1001);

    }
}