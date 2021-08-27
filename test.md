# todo-api
todo app with authorization

QUESTION 1:
1. Provide implementation for searchByName() methods

Ans:
    
    a. searchByName() implementation of Student.
        public Student searchByName(String name) {
            for (Student student : students) {
                if(student.getName().equals(name))
                    return student;

            }
            return null;
        }

    b. searchByName() implementation of Staff.
        public Staff searchByName(String name) {
            for (Staff staff : staffs) {
                if(staff.getName().equals(name))
                    return staff;
                }
            return null;
        }


2. Looking at the above defined classes we can notice that there are a lot in common between them, is such a redundancy a bad or good
    practice? why?

Ans:

    Yes there is a redundant code. And it is bad practice.
    Because we have same implementation for add & search methods in both the repo's.
    We can have one implementation and use it in both the repo's.

3. Let us start by enhancing Student and Staff classes. They have common functionalities, we need to extract them to a separate Person
    class. Provide an implementation of such a class performing needed changes to Student and Staff classes.

Ans:

    public class Person {
        private String name;
        private String address;
        private String school;
        private String clazz;

        // Setters, getters and constructors omitted for brevity
    }

    public class Student extends Person {
        private double fee;
    }

    public class Staff extends Person {
            private double salary;
    }

4. Now let us move toStudentRepo and StaffRepo classes; they seem very similar, to remove this redundancy we want to replace them
    with a single PersonRepo class that can hold Student or Staff items (i.e. an instance of PersonRepo can hold either Student or
    Staff instances) in a type safe way. Provide an implementation for such a PersonRepoclass.

Ans:

    public class PersonRepo {
        private List<Person> people = new ArrayList<>();
        public void addPerson(Person s){
            people.add(s);
        }
        public Person searchByName(String name) {
            for (Person person : people) {
                if(person.getName().equalsIgnoreCase(name))
                    return person;
            }
            return null;
        }
    }

5. Requirement was changed and now we want to enable PersonRepo class to hold both types Student and Staff. Provide a modified
    implementation to reflect this change.

Ans:

    public class PersonRepo {
        private List<Person> people = new ArrayList<>();
        public void addPerson(Person s){
            people.add(s);
        }
        public Person searchByName(String name) {
            for (Person person : people) {
                if(person.getName().equalsIgnoreCase(name))
                    return person;
            }
            return null;
        }
    }

6. Add a new searchByName() method that takes a second kind parameter (of type to be determined) returning Student type in case
    kind was student and returning Staff type in case kind was staff.

Ans:

    public class PersonRepo {
        private List<Student> students = new ArrayList<>();
        private List<Staff> staffs = new ArrayList<>();
        private static final String STUDENT = "student";
        private static final String STAFF = "staff";

        public Person searchByName(String name, String type) {
            if(STUDENT.equalsIgnoreCase(type)) {
                for (Student student : students) {
                    if(student.getName().equalsIgnoreCase(name))
                        return student;
                }
            } else if(STAFF.equalsIgnoreCase(type)) {
                for (Staff staff : staffs) {
                    if(staff.getName().equalsIgnoreCase(name))
                        return staff;
                }
            }
            return null;
        }
    }

QUESTION 2:
1. Add implementation for removeItemById()and getItemById() methods.

Ans:

    public class ItemRepo {
        private Set<Item> items = new HashSet<>();
        public void putItem(Item item){
            items.add(item);
        }
        public void removeItemById(int itemId){
            items.stream()
                    .filter(item -> item.getId() == itemId)
                    .findAny()
                    .ifPresent(item -> items.remove(item));
        }
        public Item getItemById(int itemId){
            for (Item item : items) {
                if(item.getId() == itemId)
                    return item;
            }
            return null;
        }
    }

2. Consider the following code snippet:

      ItemRepo repo = new ItemRepo();
      repo.putItem(new Item(1001,"Joe"));
      // Name 'Joe' was wrong name so we want to fix it as 'Jonathan'
      repo.putItem(new Item(1001,"Jonathan"));

    Did statement on line 4 fix the name inserted item at line 2 or we have now a duplicate item?
    If we have duplicate item then how can we
    fix this problem? we need to have any two items with same id be treated as same one.

Ans:

        No the new name inserted will not replace the old one which has same id.
        We have 2 items with same id but different name.
        We can fix the problem by overriding equals & hashcode methods in Item class.

3. Looking back at implementation of the different ItemRepo methods; Is Set best collection type to be used in terms of performance? Or
   we have a better alternative collection type?

Ans:

       Since we are adding, removing, searching in the ItemRepo.
       - In this scenario we can use LinkedHashSet for better performance. And also maintains the insertion order.
       - Its not recommended to use ArrayList, Since it is index based in worst case scenario,
        we will have 0(n) time complexity when we perform adding or removing the items from arraylist.


