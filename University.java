import java.util.ArrayList;
import java.util.Random;

public class University {
	private String name;
	private int capacity;
	private int passingScore;
	private ArrayList<Student> studentList;
	private Random r = new Random();

	public University() {
		// capacity = r.nextInt(10) + 1;
		capacity = 2;
		this.setPassingScore(r.nextInt(200) + 200);
		this.studentList = new ArrayList<>();
	}

	public University(int limitStudent, int minimumPassingScore) {
		this.capacity = limitStudent;
		this.setPassingScore(minimumPassingScore);
		this.studentList = new ArrayList<>();
	}

	int indexOfCurrentPreferenceInPreferenceList(ArrayList<Preference> preference) {
		for (int preference2 = 0; preference2 < preference.size(); preference2++)
			if (preference.get(preference2).getUniversity().getName().equals(this.name))
				return preference2;
		return -1;
	}

	private double discountedScore(Student student) {
		int indexOfPreference = indexOfCurrentPreferenceInPreferenceList(student.getPreferenceList());
		return student.getScore() * Math.pow(Main.delta, indexOfPreference);
	}

	private void sort() {
		// System.out.println("\nbefore");
		// System.out.println(studentList);
		int n = studentList.size();
		Student temp;
		for (int i = 0; i < n; i++)
			for (int j = 1; j < (n - i); j++)
				if (discountedScore(studentList.get(j - 1)) < discountedScore(studentList.get(j))) {
					temp = studentList.get(j - 1);
					studentList.set(j - 1, studentList.get(j));
					studentList.set(j, temp);
				}

		// System.out.println("after");
		// System.out.println("Student list is :" + studentList);
	}

	private void cleanStudents() {
		for (int i = capacity; i < studentList.size(); i++) {
			studentList.get(i).getPreferenceList().get(indexOfCurrentPreferenceInPreferenceList(studentList.get(i).getPreferenceList())).setState(State.rejected);
			System.out.println("⛔️" + studentList.remove(i) + " was removed from " + name);
		}
	}

	void processStudent(Student student) {
		int indexOfPreference = indexOfCurrentPreferenceInPreferenceList(student.getPreferenceList());

		sort();

		System.out.println("\n\n\n⏱ University " + this + " \nrecives:\t " + student);

		if (availableSeats() > 0) {
			studentList.add(student);
			student.getPreferenceList().get(indexOfPreference).setState(State.tentativelyAccepted);
			System.out.println("✅" + student.getName() + " is accepted to " + name);
			System.out.println("👤 Current Student List of " + name + ":\n" + studentList);
			return;
		}

		// System.out.println(name + " is full");
		// System.out.println("students: " + studentList);

		int index = -1;

		for (int i = studentList.size() - 1; i >= 0; i--)
			if (discountedScore(student) > discountedScore(studentList.get(i)))
				index = i;
			else
				break;

		if (index != -1) {
			studentList.add(index, student);
			student.getPreferenceList().get(indexOfPreference).setState(State.tentativelyAccepted);
			System.out.println("♻️" + student.getName() + " is accepted to " + name);
		} else {
			System.out.println("❌" + student.getName() + " is rejected by " + name);
			student.getPreferenceList().get(indexOfPreference).setState(State.rejected);
		}

		cleanStudents();
	}

	public ArrayList<Student> printStudentList() {
		return studentList;
	}

	int availableSeats() {
		return capacity - studentList.size();
	}

	@Override
	public String toString() {
		return getName() + " 🏫 :" + " Capacity: " + capacity + " \t#Students: " + studentList.size() + "\t Students: \n" + studentList;
	}

	public int getPassingScore() {
		return passingScore;
	}

	public void setPassingScore(int passingScore) {
		this.passingScore = passingScore;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCapacity() {
		return capacity;
	}

}
