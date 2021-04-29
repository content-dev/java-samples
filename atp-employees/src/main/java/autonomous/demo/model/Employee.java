package autonomous.demo.model;

public class Employee {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;
    private final String birthDate;
    private final String title;
    private final int department;

    private Employee(String id, String firstName, String lastName, String email, String phone, String birthDate,
                     String title, int department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.title = title;
        this.department = department;
    }


    public static Employee of(String id, String firstName,
                              String lastName, String email,
                              String phone, String birthDate,
                              String title, int department) {

        Employee e = new Employee(id, firstName, lastName, email, phone, birthDate, title, department);
        return e;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getTitle() {
        return title;
    }

    public int getDepartment() {
        return department;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(" \"employee\" { ").append("\n");
        sb.append("   \"id\" : \"").append(getId()).append("\",\n");
        sb.append("   \"first-name\" : \"").append(getFirstName()).append("\",\n");
        sb.append("   \"last-name\" : \"").append(getLastName()).append("\",\n");
        sb.append("   \"email\" : \"").append(getEmail()).append("\",\n");
        sb.append("   \"phone\" : \"").append(getPhone()).append("\",\n");
        sb.append("   \"birth-date\" : \"").append(getBirthDate()).append("\",\n");
        sb.append("   \"title\" : \"").append(getTitle()).append("\",\n");
        sb.append("   \"department\" : ").append(getDepartment()).append("\n");
        sb.append(" } ");

        return sb.toString();

    }
}
